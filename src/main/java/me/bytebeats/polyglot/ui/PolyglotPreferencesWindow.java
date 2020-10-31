package me.bytebeats.polyglot.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import me.bytebeats.polyglot.dq.DailyQuoter;
import me.bytebeats.polyglot.util.StringResUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;

/**
 * @Author bytebeats
 * @Email <happychinapc@gmail.com>
 * @Github https://github.com/bytebeats
 * @Created on 2020/10/31 16:23
 * @Version 1.0
 * @Description TO-DO
 */

public class PolyglotPreferencesWindow implements Configurable {
    private JPanel polyglot_pref_panel;
    private JCheckBox daily_quote_switch;
    private JComboBox<String> daily_quote_providers;
    private JComboBox<String> preferred_lang_provider;
    private JRadioButton dict_you_dao_rb;
    private JTextField dict_app_id;
    private JTextField dict_app_key;
    private JLabel preferred_lang;
    private JCheckBox consult_dict_cb;
    private JLabel dict_app_id_label;
    private JLabel dict_app_key_label;
    private JComboBox dict_cb;
    private JLabel dict_label;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return polyglot_pref_panel.getToolTipText();
    }

    @Override
    public @Nullable
    JComponent createComponent() {
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
        consult_dict_cb.addItemListener(e -> {
            boolean on = e.getStateChange() == ItemEvent.SELECTED;
            dict_you_dao_rb.setEnabled(on);
            PolyglotSettingState.getInstance().setSelectWordToConsultOn(on);
        });
        return polyglot_pref_panel;
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
        state.setSelectWordToConsultOn(consult_dict_cb.isSelected());
    }

    @Override
    public void reset() {
        daily_quote_switch.setSelected(PolyglotSettingState.getInstance().isDailyQuoterOn());
        consult_dict_cb.setSelected(PolyglotSettingState.getInstance().isSelectWordToConsultOn());
        daily_quote_providers.setSelectedItem(PolyglotSettingState.getInstance().getDailyQuoter());
    }

    @Override
    public void disposeUIResources() {

    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return polyglot_pref_panel;
    }
}
