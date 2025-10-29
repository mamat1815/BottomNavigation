#Aplikasi ToDo

Aplikasi daftar tugas (ToDo) sederhana yang dibuat menggunakan Jetpack Compose. Aplikasi ini mendemonstrasikan implementasi Bottom Navigation, arsitektur MVVM modern, persistensi data dengan Room, dan mekanisme pengiriman data antar layar menggunakan Parcelable.

## 🚀 Fitur Utama

* **Daftar Tugas:** Menampilkan semua tugas di layar utama.
* **Tambah Tugas:** Menambahkan tugas baru melalui layar terpisah.
* **Detail & Edit Tugas:** Melihat detail, mengedit, dan menyimpan perubahan tugas.
* **Update Status:** Menandai tugas sebagai selesai atau belum selesai.
* **Hapus Tugas:** Menghapus tugas dari daftar.
* **Persistensi Data:** Data tugas disimpan secara lokal di perangkat menggunakan Room Database.

---

## 📸 Tangkapan Layar

| Layar Utama | Layar Tambah | Layar Detail | Layar Edit |
| :---: | :---: | :---: |:---: |
| ![Placeholder Layar Utama](https://github.com/mamat1815/BottomNavigation/blob/master/Home.jpg) | ![Placeholder Layar Tambah](https://github.com/mamat1815/BottomNavigation/blob/master/Add.jpg) | ![Placeholder Layar Detail](https://github.com/mamat1815/BottomNavigation/blob/master/Edit.jpg) |![Placeholder Layar Detail](https://github.com/mamat1815/BottomNavigation/blob/master/Detail.jpg) |

---

## 🧭 Penjelasan Pola Navigasi

Aplikasi ini mengimplementasikan **dua pola navigasi** yang berbeda:

### 1. Navigasi Internal (Compose Navigation)

Pola ini digunakan untuk navigasi utama antar tab di `BottomNavigationBar`.

* **File Utama:** `MainActivity.kt`, `MyBottomNavbar.kt`, `Screen.kt`
* **Mekanisme:**
    1.  `MainActivity` bertindak sebagai host navigasi utama. Ia membuat `val navController = rememberNavController()`.
    2.  `Scaffold` di `MainActivity` memuat `MyBottomNavBar` di slot `bottomBar`.
    3.  Ketika item di `MyBottomNavBar` diklik, ia memanggil `navController.navigate(item.screen.route)`. Rute didefinisikan di `Screen.kt` (misal: "home" dan "list").
    4.  `AppNavHost` di `MainActivity` mendengarkan perubahan rute ini dan menampilkan Composable yang sesuai (`HomeScreen` atau `ListScreen`).

### 2. Navigasi Eksternal (Intent ke Activity)

Pola ini digunakan untuk berpindah dari daftar tugas di `HomeScreen` ke `Activity` baru untuk melihat detail (`DetailActivity`).

* **File Utama:** `HomeScreen.kt`, `DetailActivity.kt`, `AndroidManifest.xml`
* **Mekanisme:**
    1.  `HomeScreen` memiliki `onClick` pada setiap `ToDoItemCard`.
    2.  `onClick` ini membuat `Intent` baru untuk meluncurkan `DetailActivity` menggunakan fungsi `DetailActivity.newIntent(context, todo)`.
    3.  Aplikasi kemudian memanggil `context.startActivity(intent)` untuk berpindah ke layar (Activity) baru.
    4.  Penggunaan Intent disini untuk penggunaan Scafold Top App Bar agar tidak double di layar jadi UI nya lebih enak di pandang.

---

## 💾 Mekanisme Pengiriman Data

Proyek ini menggunakan dua mekanisme pengiriman data yang berbeda.

### 1. Pengiriman Data Eksplisit (Menggunakan `@Parcelize` / Parcelable)

* **File Kunci:** `ToDo.kt`, `DetailActivity.kt`
* **Alur:**
    1.  **Parcelize:** Model data `data class ToDo` dianotasi dengan `@Parcelize` dan mengimplementasikan `Parcelable`. Ini memungkinkan objek `ToDo` untuk "dipaketkan" dan dikirim.
    2.  **Mengirim (Put Extra):** Saat membuat `Intent` di `DetailActivity.newIntent`, objek `todo` disisipkan ke dalam intent menggunakan `putExtra(TODO_EXTRA_KEY, todo)`.
    3.  **Menerima (Get Extra):** Di `DetailActivity`, objek `todo` diekstrak kembali dari `Intent` menggunakan `intent?.getParcelableExtra(TODO_EXTRA_KEY)`. Objek ini kemudian diteruskan ke `DetailScreen` untuk ditampilkan.

### 2. Pengiriman Data Implisit (via Shared ViewModel & Flow)

Mekanisme ini digunakan secara reaktif untuk memperbarui `HomeScreen` setelah data ditambahkan di `ListScreen`.

* **File Kunci:** `ListScreen.kt`, `HomeScreen.kt`, `ToDoViewModel.kt`, `TodoDao.kt`
* **Alur:**
    1.  `ListScreen` memanggil `toDoViewModel.addTodo(...)` untuk menyimpan tugas baru.
    2.  `ToDoViewModel` memasukkan data ke **Room Database** melalui `ToDoRepository`.
    3.  `TodoDao` mengekspos daftar tugas sebagai `Flow<List<ToDo>>`. Ini adalah aliran data reaktif.
    4.  `HomeScreen` mengamati (`collectAsState`) `allTodos` dari `ToDoViewModel` yang sama.
    5.  Ketika data baru masuk ke database, `Flow` secara otomatis memancarkan daftar baru, dan UI `HomeScreen` langsung diperbarui (recomposes) untuk menampilkan tugas baru tersebut.

---

## 🛠️ Kerapian Kode, dalam aplikasi ini menggunakan **Arsitektur MVVM (Model-View-ViewModel)**:

* **View (UI):** `HomeScreen.kt`, `ListScreen.kt`, `DetailScreen.kt` (Hanya bertanggung jawab untuk menampilkan UI).
* **ViewModel:** `ToDoViewModel.kt` (Menjembatani UI dan data, berisi logika UI).
* **Model (Repository & Data):** `ToDoRepository.kt`, `AppDatabase.kt`, `TodoDao.kt`, `ToDo.kt` (Bertanggung jawab untuk logika bisnis dan pengambilan data).
