# 🕋 Dzikir Terus (Dzikir Counter)
> **Aplikasi Tasbih Digital Minimalis, Elegan, dan Tenang untuk Menjaga Rutinitas Dzikir Anda.**

Aplikasi **Dzikir Terus** dirancang khusus untuk memberikan pengalaman berdzikir yang khusyuk, modern, dan bebas gangguan. Dengan perpaduan desain bertema Islami klasik yang mewah (hijau botol, krem hangat, dan aksen emas) serta teknologi Jetpack Compose yang responsif, aplikasi ini membantu Anda menghitung dzikir harian Anda dengan mudah di mana saja dan kapan saja.

---

## ✨ Fitur Utama (Core Features)

Aplikasi ini dilengkapi dengan berbagai fitur premium untuk menunjang kenyamanan ibadah Anda:

### 1. 🔘 Counter Interaktif (Tasbih Digital)
*   **Layar Sentuh Penuh:** Cukup ketuk di mana saja pada layar untuk menambah hitungan. Tidak perlu presisi menekan tombol kecil, sehingga Anda bisa fokus berdzikir tanpa melihat layar secara terus-menerus.
*   **Progress Arc Dinamis:** Busur lingkaran emas yang melingkari angka total dzikir secara visual melacak putaran (cycle) Anda saat ini.
*   **Animasi Halus:** Transisi angka dan busur lingkaran yang responsif serta memanjakan mata.

### 2. 📿 Target Siklus yang Fleksibel (Cycle Target)
*   Mendukung target siklus standar ibadah yaitu **33**, **34**, **99**, atau **100** kali bacaan dalam satu putaran.
*   Ketika siklus tercapai, counter putaran saat ini akan otomatis kembali ke 1 dan menambah catatan siklus yang selesai.

### 3. 🎯 Haptic & Audio Feedback (Sensasi Getar & Suara)
*   **Getaran Setiap Ketukan (Vibration on Tap):** Memberikan feedback getaran halus saat layar diketuk agar Anda yakin hitungan bertambah.
*   **Getaran Siklus Penuh (Vibration on Max Cycle):** Getaran yang lebih panjang dan kuat ketika target siklus (misal 33) telah tercapai, menandakan waktu berpindah bacaan atau selesai.
*   **Suara Klik Opsional (Click Sound):** Bunyi klik halus untuk menyertai setiap ketukan Anda.

### 4. 📖 9 Pilihan Bacaan Dzikir Utama
Dilengkapi dengan teks Arab asli, transliterasi Indonesia, serta artinya:

| No | Bacaan Dzikir | Lafal Arab | Arti / Terjemahan |
|---|---|---|---|
| 1 | **Subhanallah** | سُبْحَانَ اللَّهِ | Maha Suci Allah |
| 2 | **Alhamdulillah** | الْحَمْدُ لِلَّهِ | Segala Puji Bagi Allah |
| 3 | **Allahuakbar** | اللَّهُ أَكْبَرُ | Allah Maha Besar |
| 4 | **La ilaha illallah** | لَا إِلَٰهَ إِلَّا اللَّهُ | Tiada Tuhan Selain Allah |
| 5 | **Astaghfirullah** | أَسْتَغْفِرُ اللَّهَ | Aku Mohon Ampun kepada Allah |
| 6 | **La haula wala quwwata illa billah** | لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ | Tiada Daya dan Kekuatan Kecuali dengan Allah |
| 7 | **Subhanallahi wabihamdihi** | سُبْحَانَ اللَّهِ وَبِحَمْدِهِ | Maha Suci Allah dan Segala Puji Bagi-Nya |
| 8 | **Subhanallahil 'azhim** | سُبْحَانَ اللَّهِ الْعَظِيمِ | Maha Suci Allah Yang Maha Agung |
| 9 | **Hasbunallahu wa ni'mal wakil** | حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ | Cukuplah Allah Bagi Kami dan Sebaik-baik Pelindung |

### 5. 📅 Riwayat Dzikir yang Lengkap (Dhikr History)
*   Mencatat total ketukan dan siklus dzikir harian Anda menggunakan database lokal.
*   Membantu Anda memantau konsistensi dan progress ibadah dari hari ke hari dengan tampilan kartu riwayat yang rapi dan elegan.

### 6. ⏰ Pengingat Harian Otomatis (Daily Reminder)
*   Mengirimkan notifikasi pengingat pada waktu yang ditentukan (default `05:30`) agar Anda tidak melewatkan dzikir pagi atau dzikir harian Anda.
*   Dapat diaktifkan dan dinonaktifkan dengan mudah dari menu Pengaturan.

### 7. 🌐 Dukungan Multi-bahasa (Multi-language Support)
Tersedia dalam 4 bahasa untuk menjangkau pengguna global:
*   Bahasa Indonesia (Default)
*   Bahasa Inggris (English)
*   Bahasa Tagalog/Filipino (Filipino)
*   Bahasa Mandarin (Chinese)

### 8. 💡 Layar Tetap Menyala (Keep Screen On)
*   Mencegah layar HP mati secara otomatis saat Anda sedang khusyuk berdzikir, sehingga proses ibadah tidak terganggu.

---

## 🎨 Desain & Estetika Premium

Aplikasi **Dzikir Terus** mengadopsi estetika bertema Islami modern dengan kombinasi warna hangat yang ramah di mata untuk penggunaan jangka panjang:
*   **Latar Belakang:** Cream lembut (`#FBF4E6`) yang menenangkan.
*   **Header & Aksen Utama:** Hijau Botol/Hutan (`#1B3D2B`) melambangkan ketenangan Islami.
*   **Aksen Emas/Oranye:** (`#FABF3A` / `#E8C26E`) melambangkan kemewahan spiritual dan progress harian yang cemerlang.
*   **Desain Geometris Islami:** Pola lengkungan kubah masjid (Islamic Arch Shape) yang diintegrasikan ke dalam layout header dan ornamen bintang segi empat.

---

## 🛠️ Detail Teknis (Tech Stack)

Bagi pengembang yang ingin memahami arsitektur aplikasi ini:

*   **Platform Utama:** Android Native
*   **Bahasa Pemrograman:** Kotlin
*   **UI Framework:** Jetpack Compose (Modern Declarative UI)
*   **Penyimpanan Data Lokal (Database):** SQLite melalui library **Room Database** (untuk menyimpan tabel `DzikirHistory` secara offline).
*   **State Management:** State Kotlin Compose (`mutableStateOf`, `remember`, `collectAsState`).
*   **Notifikasi & Background Task:** `AlarmManager` dipadukan dengan `BroadcastReceiver` (`ReminderReceiver`) untuk penjadwalan alarm pengingat harian yang andal bahkan saat aplikasi ditutup.
*   **Preferences:** `SharedPreferences` untuk menyimpan konfigurasi user (bacaan yang dipilih, target siklus, pengaturan getar, suara, dan bahasa).

---

## 📂 Informasi Berkas Proyek (Project Assets)

*   **Logo Utama Aplikasi:** [logo.png](file:///d:/create%20something/Mobile%20app/dzikir-terus/public/logo.png) (Logo beresolusi tinggi bertema hijau dan emas).
*   **Gambar Latar Belakang Utama:** [main-bg.png](file:///d:/create%20something/Mobile%20app/dzikir-terus/public/main-bg.png) / [main-background.png](file:///d:/create%20something/Mobile%20app/dzikir-terus/public/main-background.png)
*   **Berkas Instalasi APK (Beta):** [dzikir-terus-version-1.0-beta.apk](file:///d:/create%20something/Mobile%20app/dzikir-terus/APK/dzikir-terus-version-1.0-beta.apk)

---

## 🚀 Cara Menjalankan & Menguji Aplikasi

### Menggunakan APK (Rekomendasi Cepat):
1.  Unduh berkas APK beta dari proyek: [dzikir-terus-version-1.0-beta.apk](file:///d:/create%20something/Mobile%20app/dzikir-terus/APK/dzikir-terus-version-1.0-beta.apk).
2.  Kirim berkas ke perangkat Android Anda.
3.  Instal berkas APK tersebut (aktifkan izin instalasi dari sumber tidak dikenal jika diminta).
4.  Aplikasi siap digunakan!

### Menggunakan Android Studio (Untuk Developer):
1.  Buka Android Studio di komputer Anda.
2.  Pilih **Open** dan arahkan ke direktori proyek ini: `d:\create something\Mobile app\dzikir-terus`.
3.  Biarkan Gradle melakukan sinkronisasi dan mengunduh dependensi yang diperlukan.
4.  Buat berkas `.env` di direktori utama dan tambahkan API Key Gemini Anda jika menggunakan fitur AI:
    ```env
    GEMINI_API_KEY=your_api_key_here
    ```
5.  Hubungkan perangkat Android fisik atau jalankan Emulator.
6.  Tekan tombol **Run** (Ikon Segitiga Hijau) di Android Studio.

---

> [!NOTE]
> Aplikasi ini dirancang agar sangat ringan dan ramah baterai. Riwayat dzikir disimpan 100% secara lokal di dalam HP Anda untuk menjamin privasi ibadah Anda sepenuhnya aman.
