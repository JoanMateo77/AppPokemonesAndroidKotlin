# PokemonAppAndroid: Pokédex con arquitectura hexagonal (en construcción)

Proyecto Android en Kotlin y Jetpack Compose para consultar Pokémon desde una API REST, organizado en cuatro capas al estilo de la arquitectura hexagonal. Hoy el repositorio contiene **la estructura de la arquitectura**: los paquetes, las clases y la responsabilidad de cada una, sin la lógica implementada todavía.

## Qué hará

El diseño, documentado en los comentarios de cada clase, contempla tres pantallas:

- **Inicio** (`HomeScreen`): listado principal de Pokémon.
- **Detalle** (`DetailScreen`): características completas de un Pokémon.
- **Tipos** (`TypesScreen`): listado de tipos, reutilizando el componente `PokemonCard`.

Ninguna de estas funciones está implementada aún; ver [Estado](#estado).

## Stack

| Área | Tecnología | Versión |
|---|---|---|
| Lenguaje | Kotlin (JVM 11) | 2.0.21 |
| Build | Android Gradle Plugin / Gradle | 8.13.1 / 8.13 |
| SDK | minSdk / targetSdk / compileSdk | 24 / 36 / 36 |
| UI | Jetpack Compose (BOM) + Material 3 | 2024.09.00 |
| Pruebas | JUnit 4 / Espresso | 4.13.2 / 3.7.0 |

Las versiones salen de `gradle/libs.versions.toml` y `app/build.gradle.kts`. El cliente HTTP (Retrofit, según los comentarios del código) y Navigation Compose todavía no están declarados como dependencias.

## Arquitectura

El código está en `app/src/main/java/com/example/pokemonappandroid/`. Las dependencias apuntan hacia el dominio: la presentación usa casos de uso, los casos de uso usan la interfaz del repositorio y la infraestructura la implementa.

```
com.example.pokemonappandroid/
├── domain/                 Núcleo: no conoce Android, Retrofit ni la UI
│   ├── model/              Pokemon, PokemonDetail: modelos que usa toda la app
│   └── repository/         PokemonRepository: contrato (puerto) de acceso a datos
├── aplication/usecase/     Casos de uso; dependen solo de la interfaz del repositorio
│                           GetPokemons, GetPokemonDetail, GetPokemonType
├── infraestructure/        Adaptadores hacia el exterior
│   ├── api/                PokemonApi: endpoints de la API REST
│   ├── dto/                PokemonListDto, PokemonDetailDto: datos tal como llegan de la API
│   ├── mapper/             PokemonMapper: DTO -> modelo de dominio
│   └── repository/         PokemonRepositoryImpl: llama a la API, mapea y devuelve dominio
└── presentation/           UI con Jetpack Compose
    ├── viewmodel/          PokemonViewModel: llama a los casos de uso y expone StateFlow
    ├── navigation/         NavGraph: rutas home, detail y types
    └── ui/                 screens/ (Home, Detail, Types), components/ (PokemonCard), theme/
```

## Cómo ejecutarlo

Requisitos: Android Studio con soporte para AGP 8.13 y compileSdk 36, y JDK 17 o superior (el que incluye Android Studio sirve). El código se compila con destino JVM 11.

1. Clona el repositorio y ábrelo en Android Studio (**File > Open**).
2. Deja que Gradle sincronice las dependencias.

Cuando el proyecto compile (hoy no lo hace, ver [Estado](#estado)), se construye e instala con:

```bash
./gradlew assembleDebug
./gradlew installDebug
```

## Decisiones técnicas

- **Cuatro capas con dependencias hacia el dominio**: el dominio y los casos de uso no saben de Retrofit ni de Compose, así que la fuente de datos se puede cambiar sin tocar la lógica.
- **DTO separados del modelo de dominio** y un mapper entre ambos: si la API cambia su formato, el cambio se queda en `infraestructure/`.
- **Un caso de uso por operación** (listado, detalle, tipos), para que el ViewModel no dependa del repositorio directamente.
- **Estado de UI con StateFlow** en el ViewModel, consumido por pantallas Compose.

## Estado

En construcción. Lo que existe hoy es la estructura de la arquitectura:

- Todas las clases de `domain`, `aplication`, `infraestructure` y `presentation` están vacías; cada una tiene un comentario con su responsabilidad.
- `MainActivity` aún muestra el saludo de la plantilla de Android Studio.
- El proyecto **no compila** en su estado actual: `Pokemon` y `PokemonDetail` son `data class` sin parámetros, y `MainActivity` importa un tema (`ui.theme.PokemonAppAndroidTheme`) que no existe.
- Falta agregar Retrofit, Navigation Compose y el permiso `INTERNET` en el manifiesto.

---

Hecho por [Joan Mateo Cardona](https://github.com/JoanMateo77).
