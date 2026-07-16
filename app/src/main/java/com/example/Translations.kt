package com.example

object Translations {
    data class AppStrings(
        val settingsTitle: String,
        val tapToCount: String,
        val totalDzikir: String,
        val resetTitle: String,
        val resetMessage: String,
        val yesReset: String,
        val cancel: String,
        
        val back: String,
        val readingTargetSection: String,
        val dzikirReading: String,
        val dzikirReadingDesc: String,
        val cycleTarget: String,
        val cycleTargetDesc: String,
        
        val themeSection: String,
        val themeColor: String,
        val themeColorDesc: String,
        
        val experienceSection: String,
        val vibration: String,
        val vibrationDesc: String,
        val vibrationMax: String,
        val vibrationMaxDesc: String,
        val clickSound: String,
        val clickSoundDesc: String,
        val keepScreenOn: String,
        val keepScreenOnDesc: String,
        
        val dailyReminder: String,
        val dailyReminderDesc: String,
        
        val languageSection: String,
        val appLanguage: String,
        val appLanguageDesc: String,
        
        val historyTitle: String,
        val totalCountsToday: String,
        val totalCyclesToday: String,
        val noHistory: String
    )

    private val indonesian = AppStrings(
        settingsTitle = "PENGATURAN",
        tapToCount = "KETUK UNTUK MENGHITUNG",
        totalDzikir = "Total Dzikir:",
        resetTitle = "Reset Hitungan",
        resetMessage = "Apakah Anda yakin ingin mereset semua hitungan kembali ke 0?",
        yesReset = "Ya, Reset",
        cancel = "Batal",
        
        back = "Kembali",
        readingTargetSection = "BACAAN & TARGET",
        dzikirReading = "BACAAN DZIKIR",
        dzikirReadingDesc = "Pilih lafal dzikir yang ingin dibaca",
        cycleTarget = "TARGET PUTARAN",
        cycleTargetDesc = "Jumlah putaran dalam satu siklus",
        
        themeSection = "TEMA TAMPILAN",
        themeColor = "Warna Tema",
        themeColorDesc = "Pilih nuansa warna aplikasi",
        
        experienceSection = "PENGALAMAN",
        vibration = "GETAR SETIAP KETUKAN",
        vibrationDesc = "Getar halus setiap kali layar diketuk",
        vibrationMax = "GETAR PUTARAN PENUH",
        vibrationMaxDesc = "Getar panjang ketika target tercapai",
        clickSound = "SUARA KLIK",
        clickSoundDesc = "Bunyi klik halus saat mengetuk",
        keepScreenOn = "LAYAR TETAP MENYALA",
        keepScreenOnDesc = "Mencegah layar mati saat berdzikir",
        
        dailyReminder = "PENGINGAT HARIAN",
        dailyReminderDesc = "Ingatkan untuk berdzikir setiap hari",
        
        languageSection = "BAHASA",
        appLanguage = "BAHASA APLIKASI",
        appLanguageDesc = "Pilih bahasa tampilan aplikasi",
        
        historyTitle = "RIWAYAT DZIKIR",
        totalCountsToday = "Total Ketukan",
        totalCyclesToday = "Total Putaran",
        noHistory = "Belum ada riwayat."
    )

    private val english = AppStrings(
        settingsTitle = "SETTINGS",
        tapToCount = "TAP TO COUNT",
        totalDzikir = "Total Dhikr:",
        resetTitle = "Reset Counter",
        resetMessage = "Are you sure you want to reset all counts back to 0?",
        yesReset = "Yes, Reset",
        cancel = "Cancel",
        
        back = "Back",
        readingTargetSection = "READING & TARGET",
        dzikirReading = "DHIKR READING",
        dzikirReadingDesc = "Select the dhikr phrase to recite",
        cycleTarget = "CYCLE TARGET",
        cycleTargetDesc = "Number of recitations in one cycle",
        
        themeSection = "DISPLAY THEME",
        themeColor = "Theme Color",
        themeColorDesc = "Choose the app's color nuance",
        
        experienceSection = "EXPERIENCE",
        vibration = "VIBRATION ON TAP",
        vibrationDesc = "Soft vibration on every tap",
        vibrationMax = "VIBRATION ON MAX CYCLE",
        vibrationMaxDesc = "Long vibration when target is reached",
        clickSound = "CLICK SOUND",
        clickSoundDesc = "Soft click sound when tapping",
        keepScreenOn = "KEEP SCREEN ON",
        keepScreenOnDesc = "Prevent screen from turning off while reciting",
        
        dailyReminder = "DAILY REMINDER",
        dailyReminderDesc = "Remind to recite dhikr every day",
        
        languageSection = "LANGUAGE",
        appLanguage = "APP LANGUAGE",
        appLanguageDesc = "Select the application display language",
        
        historyTitle = "DHIKR HISTORY",
        totalCountsToday = "Total Counts",
        totalCyclesToday = "Total Cycles",
        noHistory = "No history yet."
    )

    private val filipino = AppStrings(
        settingsTitle = "MGA SETTING",
        tapToCount = "PUMINDOT PARA MAGBILANG",
        totalDzikir = "Kabuuang Dhikr:",
        resetTitle = "I-reset ang Bilang",
        resetMessage = "Sigurado ka bang nais mong i-reset ang lahat ng bilang sa 0?",
        yesReset = "Oo, I-reset",
        cancel = "Kanselahin",
        
        back = "Bumalik",
        readingTargetSection = "BABASAHIN AT TARGET",
        dzikirReading = "DHIKR NA BABASAHIN",
        dzikirReadingDesc = "Piliin ang dhikr na nais basahin",
        cycleTarget = "TARGET NG SIKLO",
        cycleTargetDesc = "Bilang ng pag-uulit sa isang siklo",
        
        themeSection = "TEMA NG DISPLAY",
        themeColor = "Kulay ng Tema",
        themeColorDesc = "Piliin ang kulay ng application",
        
        experienceSection = "KARANASAN",
        vibration = "PANGANGATOG SA PAG-TAP",
        vibrationDesc = "Banayad na pag-vibrate sa bawat pag-tap",
        vibrationMax = "PANGANGATOG SA MAX CYCLE",
        vibrationMaxDesc = "Mahabang pag-vibrate kapag nakamit ang target",
        clickSound = "TUNOG NG PAG-KLIK",
        clickSoundDesc = "Banayad na tunog ng pag-klik",
        keepScreenOn = "PANATILIHING BUKAS ANG SCREEN",
        keepScreenOnDesc = "Pigilan ang pagpatay ng screen habang nag-dhikr",
        
        dailyReminder = "ARAW-ARAW NA PAALALA",
        dailyReminderDesc = "Ipaalala na mag-dhikr araw-araw",
        
        languageSection = "WIKA",
        appLanguage = "WIKA NG APP",
        appLanguageDesc = "Piliin ang wika ng display ng application",
        
        historyTitle = "KASAYSAYAN NG DHIKR",
        totalCountsToday = "Kabuuang Bilang",
        totalCyclesToday = "Kabuuang Siklo",
        noHistory = "Wala pang kasaysayan."
    )

    private val chinese = AppStrings(
        settingsTitle = "设置",
        tapToCount = "点击计数",
        totalDzikir = "总计赞念:",
        resetTitle = "重置计数",
        resetMessage = "您确定要将所有计数重置为 0 吗？",
        yesReset = "是的，重置",
        cancel = "取消",
        
        back = "返回",
        readingTargetSection = "阅读与目标",
        dzikirReading = "赞念经文",
        dzikirReadingDesc = "选择您要诵读的赞念短语",
        cycleTarget = "循环目标",
        cycleTargetDesc = "每个循环的诵读次数",
        
        themeSection = "显示主题",
        themeColor = "主题颜色",
        themeColorDesc = "选择应用程序的颜色风格",
        
        experienceSection = "体验",
        vibration = "点击振动",
        vibrationDesc = "每次点击时轻微振动",
        vibrationMax = "满圈振动",
        vibrationMaxDesc = "达到目标圈数时长振动",
        clickSound = "点击声音",
        clickSoundDesc = "点击时的轻微声音",
        keepScreenOn = "保持屏幕常亮",
        keepScreenOnDesc = "赞念时防止屏幕关闭",
        
        dailyReminder = "每日提醒",
        dailyReminderDesc = "每天提醒进行赞念",
        
        languageSection = "语言",
        appLanguage = "应用语言",
        appLanguageDesc = "选择应用程序的显示语言",
        
        historyTitle = "赞念记录",
        totalCountsToday = "总计数",
        totalCyclesToday = "总循环数",
        noHistory = "暂无记录。"
    )

    fun getStrings(langCode: String): AppStrings {
        return when (langCode) {
            "en" -> english
            "tl" -> filipino
            "zh" -> chinese
            else -> indonesian
        }
    }
}
