
**Project Name**

*Loading Images.*

**Description**

- using the Ktor dependency to get network responses.
- Using the Koin framework for dependency injection.
- Gradle Kotlin DSL setup.
- dependency versions managed via the version catalogue (libs.version.toml).
- Use the 5 Koin module (network module,API module, repositories module, usecases module,viewModeModule).


**Getting Started**

1. **Clone the repository:**

```bash
git clone https://github.com/jaypatel9912/LoadingImages.git
```

2. **Navigate to the project directory:**

```bash
cd Loading Images
```

3. **Install dependencies:**

```bash
./gradlew install
```

**Project Structure**

This section provides an overview of your project's codebase organization. Briefly explain the purpose of each directory and some of the essential files within them.

* **api**
    * `ApiService.kt`: This file encapsulates the logic for interacting with your API and fetching image data.
* **cache (if applicable)** (Consider removing this line if there's no cache functionality)
    * `CacheStore.kt`: This file implements caching functionalities for image data retrieved from the API, optimizing performance and reducing network usage.
* **ui**
    * `PhotoListScreen.kt`: This file defines the user interface components responsible for displaying the list of photos fetched from the API.
    * `PhotoListScreenViewModel.kt`: This file implements the ViewModel logic for PhotoListScreen, managing data and exposing methods for UI interaction.
* **data**
    * `PhotoRepositoryImpl.kt`: This file implements the logic for retrieving photos, potentially interacting with both `ApiService` and `CacheStore` for optimal data access.
    * `PhotosUseCasesImpl.kt`: This file houses the UseCase implementations for fetching photos, encapsulating specific business logic of the application.

*Cache logic*
```bash
    val imageUrl = URL(url)
    val inputStream = imageUrl.openConnection().getInputStream()
    val bitmap = BitmapFactory.decodeStream(inputStream)

     fun saveImage(bitmap: Bitmap, context: Context, imageId: String?) {
        val storageDir: File = context.cacheDir
         val filePath = File(storageDir, "$imageId.jpg").path
       try {
           FileOutputStream(filePath).use { out ->
            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                out
            )
        }
      } catch (e: IOException) {
        e.printStackTrace()
      }
     }


    fun getImages(context: Context): ArrayList<File> {
       val listFiles: ArrayList<File> = arrayListOf()
       val storageDir: File = context.cacheDir
       storageDir.listFiles()?.forEach {
          if (it.name.contains(".jpg")) {
            listFiles.add(it)
         }
      }
    return listFiles
    }
```

**Usage**

* Describe the app's functionalities and user interactions in a clear and concise way. Here are some potential points to cover based on your descriptions:
    * Offline functionality (displaying cached images)
    * Network error handling
    * Pagination for loading more images
    * Toast messages for network connectivity issues
    * "Back to Top" view functionality (if applicable)






