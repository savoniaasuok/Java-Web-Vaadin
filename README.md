# Lumen README

Ohjelma käynnistyy jommalla kummalla alla olevalla komennolla. Itse olen käyttänyt ensimmäistä vaihtoehtoa pääosin.

```bash
mvn spring-boot:run
```

Tai

```bash
./mvnw spring-boot:run
```

Ja selaimeen

```bash
localhost:8080/login
```

Ctrl + C komennolla ohjelma sulkeutuu

## Sivun idea
Tälle sivustolle on jatkossa aikomus lisätä antureita, jotka mittaavat mm. valoisuutta, lämpötilaa/kosteutta, sijaintia, 
uv-indeksiä, tuulen nopeutta yms. minkä avulla voidaan paikantaa paras mahdollinen sijainti aurinkopaneelijärjestelmälle.
Järjestelmä on suunnattu kotitalouksille ja harrastelijoille mutta en näe mitään ongelmaa etteikö pienet aurinkovoimalatkin
voisi tätä hyödyntää.
Tallennetusta datasta voi päätellä missä kohtaa esim. kaava-alueella olevalla tontilla on parhaimmat edellytykset tuottaa
aurinkosähköä ympärivuoden.


## Sivun käyttö

Sivulle kirjaudutaan tällä hetkellä admin/admin tunnuksilla. Navibarista valitaan ensin "Anturit" välilehti jonne luodaan anturi.
Sen jälkeen voidaan lisätä mittaus "Mittaukset" välilehdeltä, josta valitaan aikaisemmin luotu anturi, jonka mittaamia
tiedot "mukamas" on. 

Todellisuudessahan anturi antaa nämä tiedot mutta jotta voin havainnollistaa sivun toiminnallisuutta
nämä tiedot syötetään nyt toistaiseksi käsin. Myös tässä esimerkissä on käytössä h2-tietokanta mikä ei ole lopullisessa
toteutuksessa käytettävä tietokanta.

Mittaukset välilehdeltä voidaan myös suorittaa haku tietyillä parametreillä. "Graafit" välilehti näyttää visuaaliseti 
tallennetut mittaukset halutulla aikavälillä.

## Itsearviointi

Vaadin flow (kuten myös tämä kurssi) on ollut mielenkiintoinen ja ehdottomasti oppimisen arvoinen projekti, eikä aiheeseen
tutustuminen jää tähän kurssin loppuessa. Java kurssi (ohjelmointi 3) oli mielenkiintoinen ja vaikka en ole web koodauksesta
erityisen innostunut niin tämä sai mielenkiinnon kipinän heräämään, ehkä nimenomaan Javan ansiosta. 
Omaa tekemistä voisi aina parantaa, eikä projekti ole läheskään vielä valmis mutta uskon että suunta on oikea.
