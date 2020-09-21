package me.bytebeats.polyglot.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import me.bytebeats.polyglot.dq.AbstractDailyQuoter;
import me.bytebeats.polyglot.lang.Lang;
import me.bytebeats.polyglot.meta.DailyQuote;
import me.bytebeats.polyglot.tlr.AbstractPolyglot;
import me.bytebeats.polyglot.util.LogUtils;
import me.bytebeats.polyglot.util.PolyglotUtils;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.List;

/**
 * @author bytebeats
 * @version 1.0
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/9/5 22:09
 * @description PolyglotWindow displays window where translation is executed.
 */

public class PolyglotWindow implements ToolWindowFactory {
    private JPanel polyglot_panel;
    private JPanel plgt_source_panel;
    private JComboBox<String> plgt_source_lang_cb;
    private JLabel plgt_source_label;
    private JLabel plgt_translator_label;
    private JComboBox<String> plgt_translator_cb;
    private JPanel plgt_source_input_panel;
    private JLabel plgt_langs_switch;
    private JTextArea plgt_source_lang_input;
    private JScrollPane plgt_source_scroll_pane;
    private JComboBox<String> plgt_target_langs_cb;
    private JLabel plgt_target_lang_label;
    private JButton plgt_translate_btn;
    private JTextArea plgt_target_lang_output;
    private JScrollPane plgt_target_lang_scroll_panel;
    private JButton plgt_target_lang_copy;
    private JPanel plgt_target_panel;
    private JPanel plgt_target_ouput_panel;
    private JPanel plgt_daily_quote_panel;
    private JLabel daily_quote_label;
    private JLabel daily_quote_content;
    private JLabel daily_quote_translation;

    private String from = PolyglotSettingState.getInstance().getFrom();
    private String to = PolyglotSettingState.getInstance().getTo();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content polyglotContent = contentFactory.createContent(polyglot_panel, StringResUtils.APP_NAME_DESC, true);
        toolWindow.getContentManager().addContent(polyglotContent);
        initComboBoxes();
        plgt_translate_btn.addActionListener(e -> {
            if (from != null && from.equals(to)) {
                plgt_target_lang_output.setText(plgt_source_lang_input.getText());
                return;
            }
            String translator = (String) plgt_translator_cb.getSelectedItem();
            assert translator != null;
            AbstractPolyglot polyglot = AbstractPolyglot.Factory.newInstance(translator);
            String result = polyglot.translate(Lang.Companion.from(from), Lang.Companion.from(to), plgt_source_lang_input.getText());
            plgt_target_lang_output.setText(result);
        });
        plgt_target_lang_copy.addActionListener(e -> {
            String content = plgt_target_lang_output.getText();
            StringSelection selection = new StringSelection(content);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            LogUtils.INSTANCE.info("Copy succeeded");
        });
        polyglot_panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                plgt_source_lang_cb.setSelectedItem(from);
                plgt_target_langs_cb.setSelectedItem(to);
                requestDailyQuote();
            }
        });
        plgt_langs_switch.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String lang = from;
                from = to;
                to = lang;
                plgt_source_lang_cb.setSelectedItem(from);
                plgt_target_langs_cb.setSelectedItem(to);
                String text = plgt_source_lang_input.getText();
                plgt_source_lang_input.setText(plgt_target_lang_output.getText());
                plgt_target_lang_output.setText(text);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void initComboBoxes() {
        for (Lang lang : PolyglotUtils.Companion.getLANGS_DEFAULT()) {
            plgt_source_lang_cb.addItem(lang.getDesc());
            plgt_target_langs_cb.addItem(lang.getDesc());
        }
        plgt_source_lang_cb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                from = (String) plgt_source_lang_cb.getSelectedItem();
                PolyglotSettingState.getInstance().setFrom(from);
                updateTranslators();
            }
        });
        plgt_target_langs_cb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                to = (String) plgt_target_langs_cb.getSelectedItem();
                PolyglotSettingState.getInstance().setTo(to);
                updateTranslators();
            }
        });
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        plgt_source_lang_cb.setSelectedItem(from);
        plgt_target_langs_cb.setSelectedItem(to);
        requestDailyQuote();
    }

    private void requestDailyQuote() {
        if (PolyglotSettingState.getInstance().isDailyQuoterOn()) {
            DailyQuote quote = AbstractDailyQuoter.Factory.newInstance(PolyglotSettingState.getInstance().getDailyQuoter()).quote();
            if (quote != null) {
                plgt_daily_quote_panel.setVisible(true);
                daily_quote_label.setText(String.format("每日一句: %s", quote.getDate()));
                daily_quote_content.setText(quote.getContent());
                daily_quote_translation.setText(quote.getTranslation());
            } else {
                plgt_daily_quote_panel.setVisible(false);
            }
        } else {
            plgt_daily_quote_panel.setVisible(false);
        }
    }

    private void updateTranslators() {// different translators may support different languages.
        plgt_translator_cb.removeAllItems();
        List<String> polyglots = PolyglotUtils.Companion.getSupportedPolyglot(Lang.Companion.from(from), Lang.Companion.from(to));
        for (String plgtDesc : polyglots) {
            plgt_translator_cb.addItem(plgtDesc);
        }
        plgt_translator_cb.setSelectedIndex(0);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;//is window visible when ide starts up.
    }

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return true;
    }
}
