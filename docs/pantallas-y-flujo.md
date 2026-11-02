# Cómo se organizan Login, Home e Info

Esta guía describe el flujo que implementa la app hoy y sirve como apoyo para presentarlo. La autenticación sigue siendo una maqueta; no hay backend de login.

## Mapa general

```text
MainActivity
  └─ AppNavigation
      ├─ Scaffold: barra superior, menú y navegación inferior
      └─ NavHost: selecciona la pantalla según Screen.route
          ├─ LoginScreen
          ├─ HomeScreen
          │   ├─ HeroCarousel
          │   ├─ ColaboraSection
          │   └─ TransparenciaSection
          └─ InfoScreen
              ├─ InfoHeroSection
              ├─ InfoProgramsSection
              ├─ InfoImpactSection
              ├─ InfoStorySection
              └─ InfoJoinSection
```

## Responsabilidad de cada parte

- **`MainActivity`** inicia Compose, aplica el tema y muestra `AppNavigation`.
- **`AppNavigation`** es el punto que conoce las rutas. Construye el `NavHost`, muestra las barras globales y conecta eventos de las pantallas con destinos existentes.
- **`LoginScreen`, `HomeScreen` e `InfoScreen`** organizan la interfaz de cada pantalla y reciben callbacks para las acciones que salen de ella. No deciden cómo se navega.
- **Los componentes de `ui/components`** dibujan secciones reutilizables y notifican eventos. Por ejemplo, `HeroCarousel` informa qué destino eligió la persona, pero no accede al `NavController`.
- **Los datos de presentación** describen lo que se dibuja. `CarouselItem` contiene el contenido del carrusel; `InfoScreenContent` centraliza los datos estáticos de Info. No son una capa de negocio ni requieren repositorios mientras sean contenido de muestra.
- Las acciones de Home transportan la ruta como texto (`Screen.Catalogo.route`, por ejemplo), en vez de comparar etiquetas visibles como “TU COMPRA”.
- **`Input`** controla solo el estado visual de mostrar u ocultar la contraseña. Login conserva los valores del formulario con `rememberSaveable`, suficiente mientras no exista autenticación real.

## Orientación de la aplicación

La actividad principal está fijada en orientación portrait mediante `android:screenOrientation="portrait"` en el manifest. Así se mantiene una única composición móvil vertical para Login, Home e Info; no hay layouts alternativos de landscape ni se necesita calcular `WindowSizeClass`.

## Ejemplo: cómo llega una acción a otra pantalla

```text
La persona toca “Ver Catálogo” en el carrusel
  → HeroCarousel emite la ruta de Screen.Catalogo
  → HomeScreen reenvía el evento
  → AppNavigation usa esa ruta para navegar
  → NavController navega al catálogo
```

El carrusel y las tarjetas de colaboración y transparencia envían la ruta configurada en sus datos; cambiar la etiqueta visible no cambia el destino.

En Info, “Ver catálogo” aparece solo en el programa de empleo y navega al catálogo. La escuela no tiene un destino de detalle implementado, por lo que su tarjeta no ofrece un botón que parezca funcional. “Ir al registro” navega al formulario existente.

## Estado actual y límites

- Login permite editar correo y contraseña, pero su botón está deshabilitado y muestra que la autenticación todavía no está conectada. No se simula un ingreso exitoso ni se mantiene un ViewModel sin uso.
- Home e Info reutilizan rutas existentes: Catálogo, Info y Registro.
- Las historias y métricas son de demostración; las historias se marcan como ilustrativas y usan un avatar vectorial local, sin atribuir una imagen a una persona real. Las cifras deben reemplazarse por datos autorizados antes de presentarse como reales.
- Los componentes no contienen el `NavController`; reciben callbacks. Eso permite entenderlos y probarlos por separado.

## Guion breve para explicarlo

1. “`MainActivity` arranca Compose y entrega el control a `AppNavigation`.”
2. “`AppNavigation` conoce las rutas y decide qué pantalla mostrar; las pantallas no navegan directamente.”
3. “Cada pantalla se arma con componentes que reciben datos y emiten eventos.”
4. “Por ejemplo, el carrusel emite la ruta de Catálogo y `AppNavigation` la usa para navegar.”
5. “La actividad está fijada en portrait para que la app mantenga una única composición móvil vertical.”
6. “Para Login dejamos estado local porque solo es una maqueta. No agregamos un ViewModel ni capas de autenticación hasta que exista ese requisito.”
