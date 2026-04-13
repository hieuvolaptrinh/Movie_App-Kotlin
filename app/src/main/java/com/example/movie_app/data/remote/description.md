# 📁 data/remote — Giao tiếp với API (Network Layer)

## Nhiệm vụ

Folder `remote` chứa toàn bộ code **gọi HTTP request lên server** — đây là cầu nối giữa app và internet.  
Thông thường dùng thư viện **Retrofit** để định nghĩa các endpoint API.

---

## Trong MVVM, `remote` thuộc tầng **Data Layer**

```
UI ──► ViewModel ──► Repository ──► [ remote ] ──► Internet/API Server
                                         ▲
                                   Bạn đang ở đây
```

---

## Thường chứa những gì?

| File | Mô tả |
|---|---|
| `ApiService.kt` | Interface định nghĩa các endpoint REST |
| `RetrofitClient.kt` | Cấu hình Retrofit: base URL, timeout, converter |
| `AuthInterceptor.kt` | Tự động gắn API key / token vào mọi request |

---

## Ví dụ code — Retrofit API Service

```kotlin
// ApiService.kt — danh sách các endpoint
interface ApiService {

    // GET https://api.themoviedb.org/3/movie/popular?api_key=xxx&page=1
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    // GET https://api.themoviedb.org/3/movie/{id}
    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDto>
}

// RetrofitClient.kt — cấu hình một lần, dùng mãi
object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

---

## Luồng dữ liệu từ API về UI

```
1. ViewModel gọi Repository.getPopularMovies()
2. Repository gọi ApiService.getPopularMovies()
3. Retrofit gửi HTTP GET request lên TMDB API
4. Server trả về JSON
5. Gson/Moshi tự parse JSON → MovieResponse (DTO)
6. Repository convert DTO → Domain Model
7. ViewModel nhận Domain Model → cập nhật LiveData/StateFlow
8. Activity/Fragment quan sát LiveData → hiển thị lên RecyclerView
```

---

## Trạng thái hiện tại của dự án

> ⚠️ Folder này hiện chưa có code — dữ liệu phim đang được hardcode trong Activity.
>
> Khi tích hợp **TMDB API** (The Movie Database), đây là nơi thêm:
> - `MovieApiService.kt` — định nghĩa endpoint
> - `RetrofitClient.kt` — cấu hình Retrofit với base URL của TMDB
