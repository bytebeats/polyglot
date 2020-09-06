package me.bytebeats.polyglot.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import me.bytebeats.polyglot.lang.Lang;
import me.bytebeats.polyglot.tlr.AbstractPolyglot;
import me.bytebeats.polyglot.util.LogUtils;
import me.bytebeats.polyglot.util.PolyglotUtils;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ItemEvent;
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

    private String from = "";
    private String to = "";

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        LogUtils.Companion.init(project);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content polyglotContent = contentFactory.createContent(polyglot_panel, StringResUtils.POLYGLOT_YOUDAO, true);
        toolWindow.getContentManager().addContent(polyglotContent);
    }

    @Override
    public boolean isApplicable(@NotNull Project project) {
        return true;
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        from = AppSettingState.Companion.getInstance().getFrom();
        to = AppSettingState.Companion.getInstance().getTo();
        initComboBoxes();
        plgt_translate_btn.addActionListener(e -> {
            String translator = (String) plgt_translator_cb.getSelectedItem();
            assert translator != null;
            AbstractPolyglot polyglot = AbstractPolyglot.Factory.newInstance(translator);
            String result = polyglot.translate(Lang.Companion.from(from), Lang.Companion.from(to), plgt_source_lang_input.getText());
            plgt_target_lang_output.setText(result);
        });
        plgt_target_lang_copy.addActionListener(e -> {
            String content = plgt_source_lang_input.getText();
            StringSelection selection = new StringSelection(content);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            LogUtils.Companion.info("Copy succeeded");
        });
    }

    private void initComboBoxes() {
        plgt_source_lang_cb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                from = (String) plgt_source_lang_cb.getSelectedItem();
                updateTranslators();
            }
        });
        plgt_target_langs_cb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                to = (String) plgt_target_langs_cb.getSelectedItem();
                updateTranslators();
            }
        });
        for (Lang lang : PolyglotUtils.Companion.getLANGS_DEFAULT()) {
            plgt_source_lang_cb.addItem(lang.getDesc());
            plgt_target_langs_cb.addItem(lang.getDesc());
        }
        plgt_source_lang_cb.setSelectedItem(from);
        plgt_target_langs_cb.setSelectedItem(to);
    }

    private void updateTranslators() {
        plgt_translator_cb.removeAllItems();
        List<String> polyglots = PolyglotUtils.Companion.getSupportedPolyglot(Lang.Companion.from(from), Lang.Companion.from(to));
        for (String plgtDesc : polyglots) {
            plgt_translator_cb.addItem(plgtDesc);
        }
        plgt_translator_cb.setSelectedIndex(0);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return true;
    }
}
