package me.bytebeats.polyglot.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import me.bytebeats.polyglot.dq.DailyQuoter;
import me.bytebeats.polyglot.meta.DailyQuote;
import me.bytebeats.polyglot.util.PolyglotUtils;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
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
    private JComboBox preferred_lang_provider;
    private JLabel preferred_lang;
    private JPanel preferred_lang_panel;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return polyglot_setting.getToolTipText();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        inflateDailyQuoterProviders();
        daily_quote_providers.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PolyglotSettingState.getInstance().setDailyQuoter(daily_quote_providers.getSelectedItem().toString());
            }
        });
        preferred_lang_provider.addItem(StringResUtils.PREFERRED_LANG_EN);
        preferred_lang_provider.addItem(StringResUtils.PREFERRED_LANG_CN);
        preferred_lang_provider.setSelectedItem(PolyglotSettingState.getInstance().getPreferredLang());
        preferred_lang_provider.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PolyglotSettingState.getInstance().setPreferredLang(preferred_lang_provider.getSelectedItem().toString());
                inflateDailyQuoterProviders();
            }
        });
        daily_quote_switch.addItemListener(e -> {
            boolean on = e.getStateChange() == ItemEvent.SELECTED;
            daily_quote_providers.setEnabled(on);
            PolyglotSettingState.getInstance().setDailyQuoterOn(on);
        });
        return polyglot_setting;
    }

    private void inflateDailyQuoterProviders() {
        daily_quote_providers.removeAllItems();
        int idx = 0;
        String dailyQuoterDesc = PolyglotSettingState.getInstance().getDailyQuoter();
        for (int i = 0; i < DailyQuoter.values().length; i++) {
            DailyQuoter quoter = DailyQuoter.values()[i];
            if (PolyglotSettingState.getInstance().isCnPreferred()) {
                daily_quote_providers.addItem(quoter.getDesc());
            } else {
                daily_quote_providers.addItem(quoter.getDescEn());
            }
            if (dailyQuoterDesc.equals(quoter.getDesc()) || dailyQuoterDesc.equals(quoter.getDescEn())) {
                idx = i;
            }
        }
        daily_quote_providers.setSelectedItem(idx);
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
