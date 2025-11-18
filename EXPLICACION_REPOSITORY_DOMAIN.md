# ¿Por qué el Repository NO va en Domain?

## 🎯 Respuesta Corta

**El Repository NO va en Domain porque:**
1. Domain debe ser **100% puro** - sin dependencias de infraestructura
2. Las interfaces de repositorio son **Puertos de Salida** (outbound ports)
3. Los Puertos pertenecen a la **capa de Aplicación**, no al Dominio
4. Domain solo contiene **lógica de negocio pura**

---

## 📚 Arquitectura Hexagonal - Conceptos Clave

### Domain Layer (Núcleo)
**¿Qué contiene?**
- ✅ Entidades (Pokemon, PokemonDetail)
- ✅ Value Objects (opcionales)
- ✅ Excepciones de dominio (NetworkException, PokemonNotFoundException)
- ✅ Lógica de negocio pura
- ✅ Reglas de negocio

**¿Qué NO contiene?**
- ❌ Interfaces de repositorio
- ❌ Dependencias de infraestructura
- ❌ Referencias a frameworks
- ❌ Contratos con el exterior

### Application Layer (Puertos)
**¿Qué contiene?**
- ✅ Casos de uso (UseCases)
- ✅ **Puertos de Entrada** (port/in) - Interfaces de casos de uso
- ✅ **Puertos de Salida** (port/out) - **Interfaces de repositorio** ⬅️ AQUÍ
- ✅ Orquestación de lógica

**¿Por qué aquí?**
- Los puertos definen **cómo la aplicación se comunica con el exterior**
- Son **contratos** que la aplicación necesita
- No son parte del dominio puro

---

## 🔍 Comparación: Domain vs Application

### ❌ Si Repository estuviera en Domain:

```kotlin
// domain/repository/PokemonRepository.kt
interface PokemonRepository {
    suspend fun getPokemonList(): Result<List<Pokemon>>
}
```

**Problemas:**
1. Domain dependería de `Result` (que está en `core/util`)
2. Domain conocería cómo se obtienen los datos (infraestructura)
3. Viola el principio de **Dependency Inversion**
4. Domain no sería puro

### ✅ Como está ahora (Correcto):

```kotlin
// application/port/out/PokemonRepository.kt
interface PokemonRepository {
    suspend fun getPokemonList(): Result<List<Pokemon>>
}
```

**Ventajas:**
1. Domain es puro - solo entidades y excepciones
2. Application define los contratos necesarios
3. Infrastructure implementa esos contratos
4. Cumple **Dependency Inversion Principle**

---

## 🏗️ Flujo Correcto en Arquitectura Hexagonal

```
┌─────────────────────────────────────────┐
│         PRESENTATION                     │
│  (ViewModel, UI, Screens)                │
└──────────────┬──────────────────────────┘
               │ llama
               ▼
┌─────────────────────────────────────────┐
│         APPLICATION                      │
│  ┌──────────────────────────────────┐   │
│  │  port/in/                        │   │
│  │  GetPokemonsUseCase (interfaz)   │   │
│  └──────────────────────────────────┘   │
│  ┌──────────────────────────────────┐   │
│  │  usecase/                        │   │
│  │  GetPokemonsUseCaseImpl          │   │
│  │  └─> usa                         │   │
│  └──────────────────────────────────┘   │
│  ┌──────────────────────────────────┐   │
│  │  port/out/                        │   │
│  │  PokemonRepository (interfaz) ⬅️  │   │
│  └──────────────────────────────────┘   │
└──────────────┬──────────────────────────┘
               │ implementa
               ▼
┌─────────────────────────────────────────┐
│      INFRASTRUCTURE                     │
│  repository/PokemonRepositoryImpl       │
│  └─> usa API, mapea DTOs               │
└──────────────────────────────────────────┘
               │ usa
               ▼
┌─────────────────────────────────────────┐
│         DOMAIN                          │
│  model/Pokemon (entidades puras)        │
│  exception/NetworkException             │
└─────────────────────────────────────────┘
```

---

## 📖 Principios que se Cumplen

### 1. **Dependency Inversion Principle (DIP)**
- Domain NO depende de nada
- Application define contratos (interfaces)
- Infrastructure implementa esos contratos
- ✅ **Domain es independiente**

### 2. **Separation of Concerns**
- Domain = Lógica de negocio
- Application = Orquestación y contratos
- Infrastructure = Implementaciones técnicas
- ✅ **Cada capa tiene su responsabilidad**

### 3. **Clean Architecture**
- Las dependencias apuntan HACIA ADENTRO
- Domain está en el centro (más interno)
- Application envuelve Domain
- Infrastructure envuelve Application
- ✅ **Dependencias correctas**

---

## 🎯 Analogía Simple

Imagina que Domain es el **corazón** de tu aplicación:

- **Domain** = El corazón (órgano vital, puro)
- **Application** = El sistema circulatorio (define cómo fluye la sangre)
- **Infrastructure** = Los vasos sanguíneos (implementación física)

El corazón NO define cómo se conecta con los vasos sanguíneos. 
Eso lo define el sistema circulatorio (Application).

---

## ✅ Conclusión

**El Repository NO va en Domain porque:**

1. ✅ Domain debe ser **independiente** y **puro**
2. ✅ Las interfaces de repositorio son **contratos de aplicación**
3. ✅ Los puertos pertenecen a la **capa de aplicación**
4. ✅ Esto permite que Domain sea **testeable** sin infraestructura
5. ✅ Sigue los principios de **Clean Architecture** y **Hexagonal Architecture**

**La estructura actual es CORRECTA:**
- `domain/` → Solo entidades y excepciones
- `application/port/out/` → Interfaces de repositorio
- `infrastructure/repository/` → Implementaciones

