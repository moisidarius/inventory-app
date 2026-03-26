 InventoryPro  - Sistem de Gestiune Inventar Auto



 Descriere Generală



InventoryPro este o aplicație full-stack complexă destinată managementului stocurilor de piese auto. Proiectul pune accent pe trasabilitate (Audit Log), performanță și portabilitate, fiind complet containerizat și optimizat pentru un flux de lucru modern.


 Stiva Tehnologică \& Arhitectură



Backend (Java Ecosystem)



Java 21 (LTS): Utilizarea celor mai noi funcționalități ale limbajului pentru managementul memoriei și sintaxă curată.



Spring Boot 3.2.0: Nucleul aplicației, gestionând Inversion of Control (IoC) și Dependency Injection (DI) prin @Autowired.



Spring Data JPA \& Hibernate: Engine-ul de ORM (Object-Relational Mapping) care traduce entitățile Java în tabele PostgreSQL, gestionând relațiile de tip @ManyToOne.



Lombok: Eliminarea codului de tip boilerplate prin generarea automată a metodelor Getter, Setter și Constructor.



Persistență \& Infrastructură



PostgreSQL 15: Bază de date relațională robustă, izolată într-un container Docker.



Docker \& Docker Compose: Orchestrarea serviciilor (App + DB), izolarea rețelei prin Bridge Network și asigurarea persistenței prin Docker Volumes.



Frontend (Stateless Integration)



Vanilla JavaScript (ES6+): Comunicare asincronă via fetch API.



Modern UI: Design responsiv, stilizat cu variabile CSS și efecte de feedback vizual.



 Disecția Arhitecturală (Deep Dive)



1\. Managementul Memoriei \& Execuție



Aplicația rulează în interiorul JVM (Java Virtual Machine), utilizând un model de memorie divizat:



Stack: Gestionează apelurile de metode și referințele locale în mod LIFO.



Heap: Alocă dinamic spațiu pentru obiectele de tip Product, Category și Transaction.



Garbage Collector: Monitorizează referințele pentru a elibera automat memoria Heap, prevenind memory leaks.



2\. Logica de Audit (Business Intelligence)



Trasabilitatea este implementată prin entitatea Transaction. Orice operațiune de tip ADĂUGARE sau ȘTERGERE este interceptată în InventoryController și logată cu un timestamp precis (LocalDateTime). Aceasta asigură un istoric imuabil al tuturor mișcărilor de stoc.



3\. Securitate Stateless



Sistemul folosește autentificare bazată pe Header-ul Authorization. Deoarece aplicația este stateless, serverul nu menține sesiuni active, verificând parola darius-admin-123 la fiecare cerere sensibilă, ceea ce permite o scalare orizontală facilă.



Instalare și Rulare



Datorită containerizării, aplicația poate fi pornită pe orice sistem care are Docker Desktop instalat, fără a necesita instalarea manuală a Java sau PostgreSQL.



 Comenzi Terminal:



Clonarea Proiectului:



git clone \[https://github.com/moisidarius/inventory-app.git](https://github.com/moisidarius/inventory-app.git)

cd inventory-app





Build și Deploy (Orchestrare):



docker-compose up --build





Această comandă compilează codul Java (Maven), construiește imaginea Docker și pornește containerele.



Acces:



Frontend: Deschide index.html în browser.



API Status: http://localhost:8080/api/v1/inventory



 Structura Bazei de Date



Tabel



Rol



Detalii Tehnice



Product



Stocare piese



Relație @ManyToOne cu Category, PK: ID (Identity)



Category



Clasificare



Nume categorie, PK: ID (Identity)



Transaction



Audit Log



Înregistrează Tip (Add/Del), Nume Produs, Data, Cantitate



📈 Reguli de Business Implementate



Alertă Stoc Critic: Logica de frontend verifică stoc <= stocMinim (default 5) și aplică clasa CSS stoc-critic (roșu + ⚠️).



Calcul Taxe: Prețul este stocat ca pretBaza, dar afișat cu TVA de 19% calculat la runtime în browser.



Persistență Garantată: Datele din PostgreSQL sunt salvate în volumul Docker postgres\_data, supraviețuind repornirilor de container.



📂 Organizarea Codului



inventory-app/

├── src/main/java/org/example/

│   ├── controller/      # Endpoint-uri REST (Entry Points)

│   ├── model/           # Entități JPA (Domain Models)

│   └── repository/      # Interfețe Spring Data (Data Access)

├── Dockerfile           # Instrucțiuni de build imagine Java

├── docker-compose.yml   # Orchestrare servicii \& rețea

└── pom.xml              # Gestiune dependințe Maven



