# System Donation

System Donation adalah aplikasi desktop berbasis **Java Swing** yang dirancang untuk mengelola sistem donasi secara digital. Aplikasi ini memungkinkan donatur untuk memberikan donasi ke berbagai kategori seperti **pendidikan, kesehatan, bencana alam, dan kemanusiaan**, serta menyediakan fitur manajemen lengkap untuk **admin** dalam memantau dan mengelola data donasi.

Aplikasi ini dibuat sebagai **tugas akhir mata kuliah Pemrograman Lanjut**, dengan fokus pada antarmuka pengguna (GUI) yang user-friendly dan pengelolaan data yang efisien.

---

### CARA KERJA

- Entry Point Aplikasi
Program dimulai dari file:
Salin kode

App.java
Fungsi main() hanya bertugas:
Menjalankan GUI menggunakan Event Dispatch Thread
Menampilkan halaman awal (WelcomePage)
Semua logika aplikasi tidak ditulis di main()
- Konsep Event-Driven Programming
Aplikasi menggunakan konsep event-driven, artinya:
Program berjalan berdasarkan aksi pengguna
Contoh event:
Klik tombol Login
Klik Tambah Donasi
Klik Edit atau Hapus
Setiap tombol memiliki ActionListener masing-masing.
- Alur Navigasi Aplikasi
Welcome Page
→ Tombol menuju Login
Login Page
→ Validasi login
Dashboard
Admin → Kelola data donasi
Donatur → Melihat riwayat donasi
Form Input / Edit → Data disimpan ke file .csv
Payment History → Menampilkan data dari file
- Pengelolaan Data (CRUD)
Create: Menambah data donasi baru
Read: Menampilkan data pada tabel
Update: Mengedit data donasi
Delete: Menghapus data donasi
Data disimpan menggunakan File Handling CSV, sehingga:
Data tidak hilang saat aplikasi ditutup
Data bisa dibuka kembali saat aplikasi dijalankan ulang
- File Handling
Data donasi disimpan dalam file:
Salin kode

donasi_data.csv
Saat aplikasi dijalankan:
File dibaca
Data dimuat ke dalam tabel
Saat data diubah:
File diperbarui otomatis
- Validasi & Exception Handling
Aplikasi menerapkan:
Validasi input kosong
Validasi angka
Try-catch untuk error file
Penanganan file tidak ditemukan

---

## 🎯 Fitur Utama

### 👤 Fitur Donatur
- Form donasi (kategori, nominal, metode pembayaran, pesan)
- Riwayat donasi
- Konfirmasi pembayaran donasi
- Edit data donasi
- Hapus donasi
- Laporan dan statistik donasi pribadi

### 🛠️ Fitur Admin
- Dashboard admin
- Manajemen seluruh data donasi
- Laporan dan statistik donasi
- Export laporan ke file `.txt`
- Pemantauan status pembayaran donasi

### ⚙️ Fitur Umum
- Sistem login admin dan donatur
- Validasi input data
- Penyimpanan data menggunakan file CSV
- UI modern (rounded component)
- Responsif pada berbagai ukuran layar

---

## 🖥️ Persyaratan Sistem

- Java Development Kit (JDK) 8 atau lebih tinggi  
- Sistem Operasi: Windows / macOS / Linux  
- RAM minimal 512 MB  
- Penyimpanan minimal 50 MB  

---

## 📦 Instalasi

### 1. Clone atau Download Proyek
```bash
git clone https://github.com/FaalIthaf/Donasi.git
cd Donasi
