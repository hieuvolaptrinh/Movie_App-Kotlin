# 📁 data/local — Lưu trữ dữ liệu cục bộ (Local Storage)

## Nhiệm vụ

Folder `local` chứa toàn bộ code liên quan đến **lưu trữ dữ liệu ngay trên thiết bị** — không cần kết nối internet.

Đây là nơi ứng dụng lưu dữ liệu để có thể dùng được khi offline, hoặc để tránh gọi API quá nhiều lần.

---

## Trong MVVM, `local` thuộc tầng **Data Layer**

```
UI (View) ──► ViewModel ──► Repository ──► [ local | remote ]
                                                ▲
                                          Bạn đang ở đây
```

Repository sẽ quyết định: "Nên lấy dữ liệu từ cache local, hay gọi API?"

---

## Thường chứa những gì?

| File/Class | Mô tả |
|---|---|
| `AppDatabase.kt` | Khởi tạo Room Database (SQLite) |
| `MovieDao.kt` | Interface chứa các câu truy vấn SQL (`@Query`, `@Insert`, `@Delete`) |
| `MovieEntity.kt` | Data class ánh xạ với bảng trong database (`@Entity`) |
| `SharedPrefsHelper.kt` | Đọc/ghi SharedPreferences (lưu token, setting,...) |

---

## Ví dụ code — Database Room

```kotlin
// MovieEntity.kt — ánh xạ bảng "movies" trong SQLite
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)

// MovieDao.kt — các thao tác với database
@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}

// AppDatabase.kt — khởi tạo Room
@Database(entities = [MovieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
```

---

## Trạng thái hiện tại của dự án

> ⚠️ Dự án hiện chưa sử dụng database hay local cache — dữ liệu phim đang được **hardcode thẳng trong Activity**.
>
> Khi mở rộng lên MVVM chuẩn, đây là nơi sẽ thêm Room Database để lưu danh sách phim yêu thích, lịch sử xem, hoặc cache kết quả API.

---

## Tại sao cần folder này?

- 🔌 **Offline support**: App vẫn hoạt động khi mất mạng
- ⚡ **Hiệu năng**: Không gọi API lại nếu đã có cache
- ❤️ **Tính năng yêu thích**: Lưu phim người dùng đã thích vào SQLite
