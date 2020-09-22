package me.bytebeats.polyglot.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/9/18 19:48
 * @Version 1.0
 * @Description SettingWindow offers preferences.
 */

public class SettingWindow implements Configurable {
    private JPanel polyglot_setting;
    private JCheckBox daily_quote_switch;
    private JComboBox daily_quote_providers;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return polyglot_setting.getToolTipText();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        daily_quote_providers.addItem(StringResUtils.QUOTOR_SCALLOP);
        daily_quote_providers.addItem(StringResUtils.QUOTOR_YOUDAO);
        daily_quote_providers.addItem(StringResUtils.QUOTOR_ICIBA);
        daily_quote_providers.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PolyglotSettingState.getInstance().setDailyQuoter(daily_quote_providers.getSelectedItem().toString());
            }
        });
        daily_quote_switch.addItemListener(e -> {
            boolean on = e.getStateChange() == ItemEvent.SELECTED;
            daily_quote_providers.setEnabled(on);
            PolyglotSettingState.getInstance().setDailyQuoterOn(on);
        });
        polyglot_setting.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                PolyglotSettingState state = PolyglotSettingState.getInstance();
                daily_quote_providers.setSelectedItem(state.getDailyQuoter());
                daily_quote_switch.setSelected(state.isDailyQuoterOn());
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
            }
        });
        daily_quote_providers.setSelectedItem(PolyglotSettingState.getInstance().getDailyQuoter());
        return polyglot_setting;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        PolyglotSettingState state = PolyglotSettingState.getInstance();
        state.setDailyQuoter(daily_quote_providers.getSelectedItem().toString());
        state.setDailyQuoterOn(daily_quote_switch.isSelected());
    }

    @Override
    public void reset() {
        daily_quote_switch.setSelected(PolyglotSettingState.getInstance().isDailyQuoterOn());
        daily_quote_providers.setSelectedItem(PolyglotSettingState.getInstance().getDailyQuoter());
    }

    @Override
    public void disposeUIResources() {
        polyglot_setting = null;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return polyglot_setting;
    }
}
