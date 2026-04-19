# Raport – Exercițiu de Măsurare a Performanței și Connection Pooling (HikariCP)

## 1. Scopul lucrării
Scopul acestei lucrări este analizarea performanței accesului la baza de date în două scenarii:
- utilizarea conexiunilor JDBC fără pooling
- utilizarea unui connection pool (HikariCP)

De asemenea, se urmărește observarea comportamentului sistemului în cazul gestionării incorecte a conexiunilor (scurgeri de conexiuni).

## 2. Tehnologii utilizate
- Java 21/24
- Hibernate ORM 6.x
- PostgreSQL
- HikariCP (connection pooling)
- JDBC Driver PostgreSQL

## 3. Sarcina A – Overhead-ul creării conexiunilor

### Metodologie
S-a implementat un test care realizează 100 de conexiuni la baza de date în două moduri:
1. Fără pooling (DriverManager – conexiune nouă la fiecare acces)
2. Cu pooling (HikariCP – reutilizarea conexiunilor existente)

Pentru ambele cazuri s-a măsurat:
- timpul total de execuție
- timpul mediu per conexiune

### Rezultate obținute

Fără connection pooling:
- Timp total: 4435.82 ms
- Timp mediu per conexiune: 44.36 ms

Cu HikariCP:
- Timp total: 15.89 ms
- Timp mediu per conexiune: 0.16 ms

Comparatie:
- Creștere de performanță: ~279x mai rapid cu pooling

### Analiză
Rezultatele arată că utilizarea connection pooling reduce semnificativ timpul de creare a conexiunilor. În varianta fără pooling, fiecare conexiune necesită inițializarea completă a comunicării cu baza de date, ceea ce implică cost ridicat.

În schimb, HikariCP reutilizează conexiunile deja create, reducând drastic timpul de răspuns.

## 4. Sarcina B – Detectarea scurgerilor de conexiuni

### Metodologie
S-a implementat un scenariu în care pool-ul a fost configurat cu dimensiune mică (ex: 3 conexiuni), iar conexiunile au fost deschise fără a fi închise.

### Rezultate
După câteva cereri consecutive, pool-ul a fost epuizat și a apărut eroarea:
Connection is not available, request timed out

### Remediere
Problema a fost rezolvată prin utilizarea corectă a resurselor cu try-with-resources:
- conexiunile sunt închise automat
- pool-ul revine la normal
- nu mai apar blocaje

## 5. Concluzii
- Connection pooling îmbunătățește semnificativ performanța
- HikariCP este eficient și rapid
- gestionarea incorectă a conexiunilor duce la epuizarea resurselor
- try-with-resources este esențial

## 6. Observații finale
Connection pooling este o optimizare critică pentru aplicațiile enterprise.