# Documentación Técnica – ChronoDrop

ChronoDrop es una aplicación móvil desarrollada con el objetivo de permitir a los usuarios crear mensajes digitales (texto, imágenes, audio y video) que serán entregados automáticamente en una fecha futura. Esta "cápsula del tiempo" moderna ofrece seguridad, privacidad y una experiencia emocional poderosa, integrando tecnología de mensajería diferida con cifrado de extremo a extremo.

---

## 📱 Stack Tecnológico

* **Frontend móvil:** React Native (multiplataforma: iOS y Android)
* **Backend:** Node.js con Express
* **Base de datos y almacenamiento:** Firebase (Authentication, Firestore, Storage)
* **Infraestructura en la nube:** Google Cloud Platform (GCP)
* **Cifrado de datos:** AES-256 (cifrado simétrico)

---

## 🧩 Arquitectura General

1. **Aplicación móvil (React Native):**

   * Registro e inicio de sesión (con Firebase Authentication)
   * Creación de cápsulas (formulario con contenido multimedia)
   * Visualización de cápsulas propias y cuenta regresiva
   * Notificaciones push (via Firebase Cloud Messaging)

2. **Backend (Node.js + Express):**

   * API RESTful segura
   * Programación de entregas futuras
   * Validación de accesos y verificación de integridad de datos
   * Firma digital y verificación de hash

3. **Firebase Services:**

   * **Authentication:** Email/contraseña, OAuth (opcional)
   * **Firestore:** Metadatos de cápsulas (usuario, fecha, estado, clave de cifrado)
   * **Firebase Storage:** Almacenamiento de archivos cifrados
   * **Cloud Functions:** Tareas programadas de verificación y entrega
   * **FCM:** Notificaciones de apertura

4. **Google Cloud Platform:**

   * Backup redundante en Google Cloud Storage
   * Monitorización y logging con Cloud Monitoring + Logging
   * Implementación de políticas de escalabilidad (Cloud Run, App Engine opcional)

5. **Cifrado:**

   * Los archivos se cifran con AES-256 directamente en el cliente (móvil)
   * La clave de cifrado se guarda en Firestore con acceso restringido
   * Cada cápsula tiene una clave única (opcional: derivación con PBKDF2)
   * Autenticación mutua y comunicación cifrada (HTTPS TLS 1.3)

---

## 🔐 Seguridad y Privacidad

* **Cifrado en cliente (E2EE):** Los archivos nunca se suben en claro
* **HTTPS en todas las rutas API**
* **Tokens de acceso de corta duración (JWT con expiración)**
* **Reglas de seguridad de Firestore y Storage por usuario**
* **Verificación de integridad (hash SHA-256 por cápsula)**
* **2FA (opcional) para usuarios premium**

---

## ⏱️ Entrega Programada

* Cloud Functions ejecuta tareas cada hora:

  * Consulta cápsulas con fecha programada alcanzada
  * Desbloquea y envía contenido al destinatario por email, push o app
  * Actualiza estado a "entregado" en Firestore
  * Notifica al remitente

---

## 📦 Contenido de Cápsula

Cada cápsula puede contener:

* Texto simple (hasta 10.000 caracteres)
* Imagen (JPG/PNG, max 10MB)
* Video (MP4, hasta 100MB, duración sugerida < 5 min)
* Audio (MP3/WAV, max 15MB)

Los archivos son cifrados en el móvil con una clave única (AES-256-CBC). El archivo cifrado se sube a Firebase Storage junto con su hash SHA-256.

---

## 🧪 Casos de Uso

* Mensajes personales para el "yo del futuro"
* Cartas programadas para cumpleaños o eventos importantes
* Mensajes post mortem (herencia digital)
* Cápsulas colaborativas (por grupo, familiares o amigos)

---

## 📊 Métricas y Logging

* Número de cápsulas creadas por usuario
* Volumen de almacenamiento ocupado
* Tasa de apertura y entregas exitosas
* Eventos de error en función de entrega (Firebase Logs + Google Cloud Monitoring)

---

## 💰 Monetización

* Plan gratuito limitado (3 cápsulas activas, hasta 25MB de datos)
* Plan premium mensual/anual:

  * Cápsulas ilimitadas
  * Almacenamiento ampliado (hasta 1GB)
  * Cifrado de grado militar con autenticación biométrica
  * Entregas post mortem (designación de heredero digital)

---

## 🧱 Roadmap Futuro

* 🌐 Portal web complementario
* 🪪 Validación biométrica para apertura
* 🪙 NFT opcional para firmar cápsulas únicas
* 🧬 Herencia y desbloqueo por familiar autorizado
* 🤖 Resumen generado por IA del contenido antes de apertura

---

## 🗓️ Planificación de Sprints (6 Sprints – 2 semanas c/u)

### Sprint 1: Preparación del entorno y prototipo de autenticación

* Configuración de repositorios, CI/CD
* Setup de Firebase y GCP
* Estructura base de la app en React Native
* Implementación básica de registro/login con Firebase Auth
* Mockups iniciales de UI

### Sprint 2: Creación de cápsulas y subida cifrada

* Formulario de creación (texto, imágenes, video, audio)
* Implementación de cifrado AES-256 en el cliente
* Subida a Firebase Storage y guardado de metadata en Firestore
* Validaciones de tamaño, formato y expiración

### Sprint 3: Backend y entrega programada

* API REST con Node.js para gestionar entregas programadas
* Cloud Function para ejecutar envíos diferidos
* Programación de notificaciones vía FCM y emails
* Pruebas de envío de cápsulas a destino

### Sprint 4: Visualización y seguimiento de cápsulas

* Pantalla de cápsulas activas, pasadas y próximas
* Cuenta regresiva y estado de cada cápsula
* Detalles y previsualización protegida (sin descifrado prematuro)
* Enlace para descarga segura en apertura

### Sprint 5: Seguridad y privacidad avanzada

* Integración de hash SHA-256 por archivo
* Protección con contraseña o huella (local auth)
* Reglas de Firestore y Storage
* Políticas de expiración de claves y tokens JWT

### Sprint 6: Refinamiento y publicación

* Pruebas E2E (móvil ↔ backend ↔ Firebase)
* Corrección de bugs y test de rendimiento
* Deploy en stores (TestFlight, Google Play Internal)
* Documentación técnica y legal (Términos, Privacidad, GDPR)

---

## 📄 Licencias y Cumplimiento

* Cumple con **GDPR**, **LOPDGDD** y políticas de privacidad internacionales
* Cifrado basado en estándares NIST
* Política de retención: cápsulas se mantienen 25 años (renovable)

---

**ChronoDrop** aspira a convertirse en la cápsula emocional más confiable del ecosistema digital. Su infraestructura basada en Firebase y Google Cloud, combinada con cifrado en cliente, garantiza longevidad, confidencialidad y accesibilidad multi

src/
├── components/
│   └── ui/
│       ├── Button.tsx
│       ├── TextInput.tsx
│       └── ScreenContainer.tsx
├── screens/
│   ├── auth/
│   │   ├── LoginScreen.tsx
│   │   └── RegisterScreen.tsx
│   └── home/
│       └── HomeScreen.tsx
├── navigation/
│   ├── AuthStack.tsx
│   ├── AppStack.tsx
│   └── RootNavigator.tsx
├── theme/
│   ├── colors.ts
│   ├── spacing.ts
│   ├── typography.ts
│   └── index.ts
├── services/
│   ├── firebase.ts
│   └── auth.ts
├── types/
│   └── auth.ts
├── utils/
│   └── validators.ts
└── assets/
    └── (logo, ilustraciones, etc.)
