# Raport scurt – aplicație Marketplace Java

## 1. Decizii de design

În implementarea aplicației am încercat să folosesc o structură cât mai clară și ușor de înțeles. Proiectul este împărțit în mai multe pachete, fiecare având un rol specific.

Pachetul **model** conține clasele care reprezintă entitățile principale ale aplicației: `User`, `Item` și `Transaction`. Aceste clase reflectă direct structura tabelelor din baza de date și conțin doar câmpuri și metode de tip getter.

Pentru accesul la baza de date am folosit pachetul **repository**. Clasele din acest pachet conțin interogările SQL și folosesc **JDBC** pentru a comunica cu baza de date PostgreSQL. Am folosit `PreparedStatement` pentru interogări parametrizate, astfel încât să fie evitat SQL injection.

Deasupra repository-urilor am creat un nivel **service**. Acesta are rolul de a separa logica aplicației de accesul direct la baza de date. Controller-ele nu apelează direct repository-ul, ci folosesc serviciile. Această separare face codul mai organizat și mai ușor de extins.

Pentru interfața grafică am folosit **JavaFX**. Am creat un `MainController` care construiește interfața și gestionează interacțiunile utilizatorului. Interfața este organizată folosind un `TabPane` cu trei tab-uri principale: utilizatori, item-uri și tranzacții. În tab-ul utilizatorilor se pot vedea utilizatorii și item-urile asociate acestora, iar aplicația permite adăugarea, modificarea și ștergerea item-urilor.

Pentru gestionarea conexiunii la baza de date am creat o clasă separată `DatabaseConnection`. Aceasta centralizează configurarea conexiunii și permite obținerea unui obiect `Connection` atunci când este necesar.

## 2. Provocări întâmpinate și soluții

Una dintre principalele provocări a fost configurarea conexiunii dintre aplicația Java și baza de date PostgreSQL. La început au apărut probleme legate de driverul JDBC și de string-ul de conexiune. Aceste probleme au fost rezolvate după verificarea configurației și a portului pe care rulează serverul PostgreSQL.

O altă dificultate a fost gestionarea corectă a resurselor JDBC. Este important ca obiectele `Connection`, `Statement` și `ResultSet` să fie închise după utilizare. Pentru a rezolva acest lucru am folosit mecanismul **try-with-resources**, care închide automat aceste resurse la finalul blocului `try`.

În partea de interfață grafică, o provocare a fost actualizarea tabelelor după modificarea datelor. De exemplu, după adăugarea sau ștergerea unui item, tabela trebuia reîncărcată pentru a reflecta modificările. Soluția a fost apelarea metodei care reîncarcă item-urile pentru utilizatorul selectat după fiecare operație de tip add, update sau delete.

De asemenea, a fost necesară validarea input-ului pentru câmpul de preț al unui item. Deoarece prețul este de tip `BigDecimal`, a trebuit să verific dacă valoarea introdusă de utilizator este un număr valid. În cazul unei valori invalide, aplicația afișează un mesaj de eroare.

## 3. Ce am învățat

Prin realizarea acestui proiect am învățat mai bine cum funcționează interacțiunea dintre o aplicație Java și o bază de date relațională folosind JDBC. Am înțeles cum se creează și se execută interogări SQL din codul Java și cum pot fi mapate rezultatele în obiecte Java.

De asemenea, am învățat mai multe despre organizarea unui proiect în straturi, folosind o separare între controller, service și repository. Această structură face codul mai clar și ajută la separarea responsabilităților.

În plus, am dobândit mai multă experiență în utilizarea **JavaFX** pentru construirea unei interfețe grafice și pentru afișarea datelor într-un `TableView`.

În general, proiectul m-a ajutat să înțeleg mai bine cum poate fi construită o aplicație completă care combină interfață grafică, logică de aplicație și acces la bază de date.
