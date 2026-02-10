# Android Clean Architecture Playground

안드로이드 클린 아키텍처 프레임

모듈 구조 및 디렉토리 구조를 설정

MVVM + Jetpack Compose + Retrofit + Room + Hilt

## Tech Stack

| 분류 | 기술 |
|---|---|
| UI | Jetpack Compose (Material3) |
| DI | Hilt |
| Network | Retrofit + OkHttp |
| Local DB | Room |
| Async | Coroutines |
| Build | Gradle Kotlin DSL + Version Catalog + Convention Plugins |

## 모듈 구조

```
cleanArchitecturePlayground/
├── app/                    # Application 진입점, Hilt 설정
├── presentation/           # UI (Compose Screen, ViewModel, Theme)
├── domain/                 # 비즈니스 로직 (UseCase, Repository 인터페이스, Model)
├── data/                   # 데이터 처리 (Repository 구현, DataSource, Entity, Mapper)
├── build-logic/            # Convention Plugins (공통 빌드 설정)
│   └── convention/         # 플러그인 소스
└── gradle/
    └── libs.versions.toml  # Version Catalog
```

### 모듈 의존성

```
app → domain, data, presentation
presentation → domain
data → domain
domain → (의존성 없음, 순수 Kotlin)
```

## 빌드 시스템

### Convention Plugins (`build-logic`)

각 모듈의 반복되는 빌드 설정을 Convention Plugin으로 통합 관리한다.

| Plugin ID | 적용 대상 | 역할 |
|---|---|---|
| `cleanarchitecture.android.application` | app | AGP Application + Kotlin + 공통 Android 설정 |
| `cleanarchitecture.android.library` | data, presentation | AGP Library + Kotlin + 공통 Android 설정 |
| `cleanarchitecture.android.compose` | app, presentation | Compose buildFeatures + composeOptions |
| `cleanarchitecture.android.hilt` | app, data, presentation | Kapt + Hilt 의존성 |
| `cleanarchitecture.kotlin.jvm` | domain | java-library + kotlin-jvm + JVM 1.8 |

### Version Catalog

모든 의존성 버전은 `gradle/libs.versions.toml`에서 중앙 관리한다.

## 계층간 의존성 규칙

### Presentation

domain 모듈에 의존하며 UI와 관련된 코드를 캡슐화한다.
Jetpack Compose를 사용하며, Screen은 상태를 갖지 않는 Stateless Composable로 작성한다.
ViewModel에서 상태를 관리하고, `observeAsState`로 Compose에 연결한다.

- **Screen** : Stateless Composable. 데이터와 콜백만 파라미터로 받는다.
- **ViewModel** : `BaseViewModel`을 상속. UseCase를 호출하고 LiveData로 상태를 노출한다.
- **Theme** : Material3 기반 커스텀 테마 (Light/Dark 지원).

### Domain

도메인 모듈은 앱의 중심부로써 포함된 비즈니스 로직은 앱을 구성하고 있는 것 중 가장 중요하다.
비즈니스 로직을 망쳐서는 안되기 때문에 이 계층은 어떠한 계층에도 의존하지 않는다.
안드로이드 프레임워크에 의존하지 않고, 순수 코틀린 코드만 작성하길 권장한다.

- **Model** : 도메인 영역을 표현하는 데이터 클래스. ex) `SampleModel`
- **UseCase** : `operator fun invoke()`를 사용하여 비즈니스 로직을 수행한다.
- **Repository 인터페이스** : 데이터 소스에 대한 추상화. 구현은 data 모듈에 위치한다.

### Data

data 모듈은 DataSource(DB, 서버 등)와 상호작용을 담당하는 코드가 포함된다.
domain 모듈에 의존한다.

- **DataSource** : `LocalDataSource`, `RemoteDataSource` 인터페이스와 구현체. Entity 타입을 반환한다.
- **Repository 구현** : DataSource를 호출하고 Mapper를 통해 Entity → Model 변환 후 반환한다.
- **UseCase 구현** : Repository에 의존한다 (DataSource 직접 참조 금지).
- **Entity** : Data Layer에서 사용하는 데이터 클래스. API/DB 원본 데이터 구조를 표현한다.
- **Mapper** : Entity → Model 변환 확장 함수. ex) `List<SampleEntity>.toSampleModels()`
- **Hilt Modules** : `NetworkModule`, `DataSourceModule`, `RepositoryModule`, `UseCaseModule`

```
Entity : 변경이 되서는 안되는 Data Class. 가장 기본이 되는 Data Class 로 Api 에서 내려오는
데이터 형식이 바뀌어도 Entity 는 영향이 없도록 해야한다.

DTO : 변경이 쉽게 가능한 Data Class. 데이터 형태나 구조가 바뀔 수 있는 경우 사용한다.
DTO 를 가공하여 Entity 에 넣어 사용하기 때문에, 실제로 사용하는 데이터가 변경되지 않는 이상
다른 구조에 영향이 없다.

Mapper : Data Entity → Domain Model 변환.
Data Layer 에서는 Entity 로 받아서 사용하지만, Domain/Presentation Layer 에서는
Model 로 사용한다. Repository가 Mapper를 호출하여 변환 후 상위 계층으로 전달한다.
```

## 데이터 흐름

```
[UI (Screen)] → [ViewModel] → [UseCase] → [Repository] → [DataSource] → [DB/API]
                                              ↓
                                         Mapper (Entity → Model)
```

## 새로운 기능 추가 가이드

1. `domain/model/`에 Model 정의
2. `domain/repository/Repository`에 메서드 추가
3. `domain/usecase/`에 UseCase 인터페이스 생성
4. `data/entity/`에 Entity 생성, `data/mapper/`에 Mapper 확장 함수 작성
5. `data/repositoryImpl/`에 Repository 구현 (Mapper로 Entity→Model 변환)
6. `data/usecaseImpl/`에 UseCase 구현 (Repository에 의존)
7. `data/hilt/`의 Hilt Module에 바인딩 추가
8. `presentation/view/`에 ViewModel 생성 (`BaseViewModel` 상속)
9. `presentation/view/`에 Stateless Screen Composable 생성

## 참고

https://www.charlezz.com/?p=45391

![toptal-blog-image-1543413671794-80993a19fea97477524763c908b50a7](https://user-images.githubusercontent.com/26853549/216267014-394973ea-5b26-458e-b00e-235a33a486be.png)

![www charlezz com-clean-architecture-diagram-flow](https://user-images.githubusercontent.com/26853549/197118067-632722ea-63f8-4cd7-9b85-817f1b963dba.png)
