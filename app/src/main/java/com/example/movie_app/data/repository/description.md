# 📁 data/repository — Triển khai Repository (Repository Implementation)

## Nhiệm vụ

Folder `repository` chứa **lớp triển khai cụ thể** của Repository Interface đã được định nghĩa ở tầng `domain`.

`Repository` đóng vai trò như một **"bộ điều phối dữ liệu thông minh"**:
- Biết khi nào nên lấy dữ liệu từ **cache (local)**
- Biết khi nào nên gọi **API (remote)**
- Chịu trách nhiệm **chuyển đổi DTO → Domain Model**

---

## Trong MVVM, `repository` là cầu nối giữa Data và Domain

```
domain/repository (Interface)
         ▲ implement
data/repository (Class cụ thể)
         │
         ├──► data/remote  (gọi API)
         └──► data/local   (đọc/ghi database)
```

---

## Thường chứa những gì?

| File | Mô tả |
|---|---|
| `MovieRepositoryImpl.kt` | Implement `MovieRepository` từ domain layer |

---

## Ví dụ code — Repository Implementation

```kotlin
// domain/repository/MovieRepository.kt (Interface — định nghĩa "hợp đồng")
interface MovieRepository {
    suspend fun getPopularMovies(): Result<List<Movie>>
    suspend fun getMovieDetail(id: Int): Result<Movie>
}

// data/repository/MovieRepositoryImpl.kt (Class — thực thi "hợp đồng")
class MovieRepositoryImpl(
    private val apiService: ApiService,      // nguồn remote
    private val movieDao: MovieDao            // nguồn local
) : MovieRepository {

    override suspend fun getPopularMovies(): Result<List<Movie>> {
        return try {
            // 1. Thử gọi API trước
            val response = apiService.getPopularMovies(API_KEY)
            if (response.isSuccessful) {
                val movies = response.body()!!.results.map { it.toDomain() }
                // 2. Lưu vào database để dùng offline
                movieDao.insertMovies(movies.map { it.toEntity() })
                Result.success(movies)
            } else {
                // 3. Nếu lỗi → lấy từ cache local
                val cached = movieDao.getAllMovies().first()
                Result.success(cached.map { it.toDomain() })
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// Extension function để convert DTO → Domain Model
fun MovieDto.toDomain() = Movie(
    id       = this.id,
    title    = this.title,
    imageUrl = "https://image.tmdb.org/t/p/w500${this.posterPath}",
    rating   = this.voteAverage
)
```

---

## Tại sao phải tách Interface (domain) và Implementation (data)?

| Lý do | Giải thích |
|---|---|
| **Dễ test** | ViewModel chỉ biết Interface → có thể fake/mock khi test |
| **Dễ thay đổi** | Đổi từ Retrofit sang Ktor? Chỉ sửa Implementation, không ảnh hưởng ViewModel |
| **Nguyên tắc SOLID** | Tuân theo Dependency Inversion Principle |

---

## Trạng thái hiện tại của dự án

> ⚠️ Folder này hiện chưa có Implementation — logic lấy dữ liệu đang nằm thẳng trong `HomeActivity.kt`.  
> Đây là bước quan trọng cần refactor khi muốn áp dụng MVVM đầy đủ.
