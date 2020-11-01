package me.bytebeats.polyglot.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import me.bytebeats.polyglot.dq.DailyQuoter;
import me.bytebeats.polyglot.lang.Lang;
import me.bytebeats.polyglot.util.PolyglotUtils;
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
    private JComboBox<String> dict_target_cb;
    private JLabel dict_target_lang;

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
        inflateDictionaryTargetLangs();
        dict_target_cb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PolyglotSettingState.getInstance().setDictLang(dict_target_cb.getSelectedItem().toString());
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
        dict_you_dao_rb.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                PolyglotSettingState.getInstance().setDict(StringResUtils.POLYGLOT_YOUDAO);
            }
        });
        consult_dict_cb.addItemListener(e -> {
            boolean on = e.getStateChange() == ItemEvent.SELECTED;
            dict_you_dao_rb.setEnabled(on);
            PolyglotSettingState.getInstance().setSelectWordToConsultOn(on);
        });
        if ("".equals(PolyglotSettingState.getInstance().getAppID())) {
            dict_app_id.setText(dict_app_id.getToolTipText());
        } else {
            dict_app_id.setText(PolyglotSettingState.getInstance().getAppID());
        }
        if ("".equals(PolyglotSettingState.getInstance().getAppKey())) {
            dict_app_key.setText(dict_app_key.getToolTipText());
        } else {
            dict_app_key.setText(PolyglotSettingState.getInstance().getAppKey());
        }
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

    private void inflateDictionaryTargetLangs() {
        dict_target_cb.removeAllItems();
        int idx = 0;
        String dictDesc = PolyglotSettingState.getInstance().getDictLang();
        for (int i = 0; i < PolyglotUtils.Companion.getDictionaryTargetLangs().size(); i++) {
            Lang lang = PolyglotUtils.Companion.getDictionaryTargetLangs().get(i);
            if (PolyglotSettingState.getInstance().isCnPreferred()) {
                dict_target_cb.addItem(lang.getDesc());
            } else {
                dict_target_cb.addItem(lang.getDescEN());
            }
            if (dictDesc.equals(lang.getDesc()) || dictDesc.equals(lang.getDescEN())) {
                idx = i;
            }
        }
        dict_target_cb.setSelectedIndex(idx);
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
        state.setAppID(dict_app_id.getText());
        state.setAppKey(dict_app_key.getText());
    }

    @Override
    public void reset() {
        dict_you_dao_rb.setSelected(true);
        daily_quote_switch.setSelected(PolyglotSettingState.getInstance().isDailyQuoterOn());
        consult_dict_cb.setSelected(PolyglotSettingState.getInstance().isSelectWordToConsultOn());
        daily_quote_providers.setSelectedIndex(0);
        dict_target_cb.setSelectedIndex(0);
        dict_app_id.setText(dict_app_id.getToolTipText());
        dict_app_key.setText(dict_app_key.getToolTipText());
    }

    @Override
    public void disposeUIResources() {

    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return polyglot_pref_panel;
    }
}
