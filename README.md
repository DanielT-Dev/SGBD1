# Marketplace Java Application

Aceasta este o aplicație desktop realizată în **JavaFX** care simulează
un marketplace simplu.\
Aplicația permite gestionarea utilizatorilor, item-urilor postate de
aceștia și a tranzacțiilor. Comunicarea cu baza de date se face folosind
**JDBC** și **PostgreSQL**.

Modelele principale din aplicație sunt:

-   `User`
-   `Item`
-   `Transaction`

Structura bazei de date reflectă direct aceste clase.

------------------------------------------------------------------------

# 1. Cum se configurează baza de date

Aplicația folosește **PostgreSQL** și conține trei tabele:

-   `users`
-   `items`
-   `transactions`

## 1.1 Crearea bazei de date

În PostgreSQL execută:

``` sql
CREATE DATABASE marketplace;
```

Conectează-te apoi la baza de date:

``` sql
\c marketplace
```

------------------------------------------------------------------------

## 1.2 Crearea tabelelor

Structura tabelelor respectă exact câmpurile din clasele din pachetul
`model`.

### Tabela `users`

Corespunde clasei `User`.

``` sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);
```

### Tabela `items`

Corespunde clasei `Item`.

``` sql
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    seller_id INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price NUMERIC(10,2) NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES users(id)
);
```

### Tabela `transactions`

Corespunde clasei `Transaction`.

``` sql
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    buyer_id INTEGER NOT NULL,
    item_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES users(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);
```

------------------------------------------------------------------------

## 1.3 Inserarea unor date de test (opțional)

``` sql
INSERT INTO users (username, email) VALUES
('john', 'john@example.com'),
('anna', 'anna@example.com');
```

``` sql
INSERT INTO items (seller_id, title, description, price) VALUES
(1, 'Laptop', 'Laptop second hand', 1500.00),
(2, 'Telefon', 'Telefon in stare buna', 900.00);
```

``` sql
INSERT INTO transactions (buyer_id, item_id, quantity, transaction_date) VALUES
(2, 1, 1, NOW());
```

------------------------------------------------------------------------

# 2. Cum se configurează conexiunea la baza de date

Conexiunea la baza de date este definită în fișierul:

    config/DatabaseConnection.java

Exemplu de configurare pentru PostgreSQL:

``` java
private static final String URL = "jdbc:postgresql://localhost:5432/marketplace";
private static final String USER = "postgres";
private static final String PASSWORD = "parola_ta";
```

Dacă baza de date, utilizatorul sau parola sunt diferite, acestea
trebuie modificate în această clasă.

Proiectul trebuie să includă **driverul JDBC pentru PostgreSQL
(`postgresql.jar`)** în librăriile proiectului.

------------------------------------------------------------------------

# 3. Cum se rulează aplicația

## 3.1 Cerințe

Pentru a rula aplicația sunt necesare:

-   **Java JDK 17 sau mai nou**
-   **PostgreSQL Server**
-   **JavaFX**
-   **driverul JDBC pentru PostgreSQL**

## 3.2 Pași pentru rulare

1.  Pornește serverul PostgreSQL.
2.  Creează baza de date și tabelele folosind scripturile din secțiunea
    **Configurarea bazei de date**.
3.  Configurează credențialele bazei de date în
    `DatabaseConnection.java`.
4.  Deschide proiectul într-un IDE (de exemplu IntelliJ sau Eclipse).
5.  Rulează clasa principală a aplicației JavaFX.

După pornire, aplicația va afișa interfața **Marketplace**, unde pot fi:

-   vizualizați utilizatorii
-   adăugate / modificate / șterse item-uri
-   vizualizate tranzacțiile
-   gestionate relațiile dintre utilizatori și item-uri.
