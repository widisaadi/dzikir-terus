<div align="center">
  <img src="public/logo.png" alt="Dzikir Terus Logo" width="120" style="border-radius: 20%; margin-bottom: 10px;" />
  <h1>🕋 Dzikir Terus (Dzikir Counter)</h1>
  <p><strong>A Minimalist, Elegant, and Serene Digital Tasbih Application to Maintain Your Daily Dhikr.</strong></p>
  
  [![GitHub release (latest by date)](https://img.shields.io/github/v/release/widisaadi/dzikir-terus?style=for-the-badge&color=1B3D2B)](https://github.com/widisaadi/dzikir-terus/releases)
  [![License](https://img.shields.io/badge/License-MIT-gold.svg?style=for-the-badge)](LICENSE)
  [![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android)](https://developer.android.com)
  
  <br />
  
  <p align="center">
    <a href="#-about-the-project">About</a> •
    <a href="#-installation--download">Download</a> •
    <a href="#-key-features">Features</a> •
    <a href="#-tech-stack">Tech Stack</a> •
    <a href="#-getting-started-for-developers">Getting Started</a> •
    <a href="#-bahasa-indonesia">Bahasa Indonesia</a>
  </p>
</div>

---

## 📱 About the Project

**Dzikir Terus** is an Android-native digital tasbih application designed to provide a focused, elegant, and distraction-free dhikr experience. Inspired by classical Islamic aesthetics, it blends a premium forest green (`#1B3D2B`), warm cream (`#FBF4E6`), and gold accents with a modern, responsive Jetpack Compose interface. 

Whether you want to track your daily routines or complete 33/99 counts, Dzikir Terus helps you focus entirely on your spiritual connection without looking at your screen constantly.

---

## 🚀 Installation & Download

> [!IMPORTANT]
> **Looking for the app?**
> You do not need to compile the source code or use Android Studio to try out the app! 
> 
> 👉 Download the latest **[dzikir-terus-v.1.0-beta-release.apk](https://github.com/widisaadi/dzikir-terus/releases/download/v.1.0/dzikir-terus-v.1.0-beta-release.apk)** directly or check the **[GitHub Releases](https://github.com/widisaadi/dzikir-terus/releases)** page.

---

## ✨ Key Features

* **🔘 Tap Anywhere Counter:** Tap anywhere on the screen to count. No need to look at the screen or aim for small buttons, allowing you to focus on your dhikr.
* **📿 Dynamic Progress Arc & Cycles:** A beautiful golden circular arc traces your progress. Supports cycle targets of **33**, **34**, **99**, or **100** with auto-resetting cycle counters.
* **🎯 Haptic & Audio Feedback:** Tactile vibration feedback on every tap, a stronger vibration once a full cycle is reached, and an optional subtle click sound.
* **📖 9 Built-in Dhikr Selections:** Complete with original Arabic text, transliteration, and translations.
* **📅 Local History Tracking:** Powered by Room DB, it tracks your daily taps and completed cycles offline to preserve your privacy.
* **⏰ Daily Automated Reminders:** Never miss your daily dhikr with automatic notifications scheduled via `AlarmManager` (configurable from settings).
* **🌐 Multilingual Support:** Available in 4 languages: English, Bahasa Indonesia, Filipino, and Chinese.
* **💡 Keep Screen On:** Option to prevent the screen from timing out while you are actively counting.

---

## 🎨 Premium Aesthetics

The visual language of Dzikir Terus uses a carefully curated Islamic-themed palette:
*   **Background:** Calming Cream (`#FBF4E6`)
*   **Primary Accent:** Elegant Forest Green (`#1B3D2B`)
*   **Progress & Success:** Spiritual Gold (`#FABF3A` / `#E8C26E`)
*   **Design Motifs:** Geometric Islamic arches and 8-pointed star patterns (Rub el Hizb) built directly into the Jetpack Compose layouts.

---

## 🛠️ Tech Stack

*   **Language:** 100% Kotlin
*   **UI Framework:** Jetpack Compose (Declarative UI)
*   **Database:** Room Database (SQLite abstraction for offline history)
*   **Background Tasks:** `AlarmManager` + `BroadcastReceiver` for reliable notifications
*   **Local Storage:** `SharedPreferences` for user preference states
*   **Design & Layouts:** Material Design 3 with custom brand shapes

---

## 💻 Getting Started (For Developers)

To run the codebase locally:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/widisaadi/dzikir-terus.git
   ```
2. **Open the project:**
   - Launch **Android Studio**.
   - Choose **Open** and select the cloned directory.
   - Let Gradle sync and resolve all dependencies.
3. **Environment Variables:**
   - Create a `.env` file in the root directory.
   - Set up your local configurations as needed (e.g. `GEMINI_API_KEY` if you integrate generative features).
4. **Build and Run:**
   - Connect your Android device or start an emulator.
   - Click the green **Run** button in Android Studio.

---

## 🇮🇩 Bahasa Indonesia

### Tentang Proyek

**Dzikir Terus** adalah aplikasi tasbih digital minimalis, elegan, dan tenang untuk menjaga konsistensi ibadah dzikir harian Anda. 

### 📥 Unduh Aplikasi
Aplikasi ini sudah dipaketkan dalam bentuk berkas APK siap pakai. Anda tidak perlu melakukan compile kode sumber untuk mencobanya:
1. Unduh berkas **[dzikir-terus-v.1.0-beta-release.apk](https://github.com/widisaadi/dzikir-terus/releases/download/v.1.0/dzikir-terus-v.1.0-beta-release.apk)** secara langsung.
2. Atau kunjungi halaman **[GitHub Releases](https://github.com/widisaadi/dzikir-terus/releases)** jika ingin melihat rilis versi lainnya.
3. Kirim berkas APK ke HP Android Anda dan instal secara langsung.

### Fitur Utama:
* **Ketuk Di Mana Saja:** Cukup ketuk layar di bagian mana saja untuk menghitung.
* **Target Putaran:** Pilihan batas siklus 33, 34, 99, atau 100 dengan indikator visual melingkar berwarna emas yang elegan.
* **Feedback Getar & Suara:** Sensasi getaran setiap ketukan dan getaran panjang saat siklus terpenuhi.
* **9 Bacaan Dzikir Utama:** Disertai lafal Arab, transliterasi Latin, dan terjemahan bahasa Indonesia.
* **Riwayat Log Lokal:** Disimpan menggunakan Room DB sehingga data riwayat aman 100% di HP Anda.
* **Pengingat Otomatis:** Dilengkapi dengan notifikasi alarm pengingat harian.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
