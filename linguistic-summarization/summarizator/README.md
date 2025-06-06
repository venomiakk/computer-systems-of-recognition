## Właściwości zbiorów rozmytych


**Zbiór rozmyty**  
$A = \{ \langle x, \mu_A(x) \rangle : x \in \mathcal{X}\}$

**Nośnik - support**  
*zbiór klasyczny*  
$supp(A) = \{ x \in \mathcal{X} : \mu_A(x) > 0 \}$

**Kardynalność - cardinality**  
*liczba, suma wartości funkcji przynależności*  
$card(A) = \sum_{x \in supp(A)} \mu_A(x) = \sum_{x\in\mathcal{X}}\mu_A(x) = \sum{-count}(A)$

**Stopień rozmycia - degree of fuzziness**  
*liczba, im większe tym zbiór rozmyty jest "gorszy" (mniej precyzyjnie definiuje określenie)*  
$in(A) = \frac{|supp(A)|}{|X|} = \frac{|supp(A)|}{card(A)}$

**Alfa przekrój - alpha cut**  
*zbiór klasyczny, podniesienie granicy przynależności do zbioru*  
$\alpha = [0,1]$  
$A_{\alpha} = \{ x \in X: u_A(x)>\alpha\}$

**wypukłość zbioru - convex**  
zbiór klasyczny jest wypukły wtedy, gdy dla każdej pary punktów odcinek łączący te dwa punkty również zawiera się w tym zbiorze
przeciwieństwem jest zbiór wklęsły (przykład: zbiór w kształcie koła a zbiór w kształcie półksiężyca) tak samo ma się to do zbiorów rozmyty (kształt funkcji przynależności - gaussoida jest wypukła - kształt gór już nie) - jeżeli dla jakiegoś alfa a-przekrój nie jest ciągły wtedy zbiór nie jest wypukły
definiowanie kwantyfikatorów rozmytych - zbiory wypukłe

## Zmienna lingwistyczna

$L = <\mathcal{L}, H(L), X,G,K>$  
$\mathcal{L}$ - nazwa zmiennej  
H(L) - zbiór możliwych wartości  
X - przestrzeń rozważań i zbiory rozmyte w niej zdefiniowane  

*G - reguła gramatyczna (wybór słów które kojarzą się z daną cechą; określa jakie słowa nie powinny opisywać cechy)*  
*K - reguła semantyczna (przyporządkowuje terminom zbiory rozmyte; chodzi o kolejność terminów i do jakich zbiorów rozmytych są przyporządkowane; [nie rosnące, nie malejące coś tam])*

## Modyfikatory lingwistyczne

- power hedges - podnoszenie funkcji zb romytych do potęgi
- shift (shifted) hedges

_potęga zbioru rozmytego_ - W wyniku operacji otrzymujemy zbiór rozmyty; jego nowa funkcja przynależności, to stara funkcja podenisiona do potęgi  
najczęściej potęgi:

- kwadrat - zbiór skoncentrowany $A_{con}$ - jakbyśmy dodali modyfikator "bardzo"
- (1/2) - zbiór rozcieńczony, roszerzenie zakresu, dilated $A_{dil}$- pierwiastek kwadratowy - jakbyśmy dodali modyfikator "niceo", "mniejwięcej"

## Łączenie zbiorów rozmytych

**dopełnienie zbioru - "NIE"**  
*zbiór rozmyty*   
$\mu A^c(x) = 1 - \mu A(x)$  
jeżeli do jakiegoś okreśłenia dokładamy "nie" np. nie ciężki  
(jakby odwrócić funkcję przynależności)

**koniunkcja - "I"**
*zbiór rozmyty*  
$\mu_{A\cap B}(x) =  min\{\mu_{A}(x),\mu_{B}(x)\}$  
**alternatywa - "LUB"**
*zbiór rozmyty*  
$\mu_{A\cup B}(x) =  max\{\mu_{A}(x),\mu_{B}(x)\}$

koniunkcja i alternatywa są

- przemienne (idempotentne)
- łączne (asoc...)

## Rodzaje zdań

### Jednopodmiotowe w pierwszej formie

**Q P are/have S_n**

- Q - kwantyfikator względny lub bezwzględny - np. "all", "some", "no"
- P - podmiot - "samochodów"
- S_n - koniunkcja sumaryzatorów - np. "Duży silnik" i "Ekonomiczny" 

### Jednopodmiotowe w drugiej formie

**Q P being/having W_n(S_n) are/have S_n**

- Q - kwantyfikator względny - np. "all", "some", "no"
- P - podmiot - "samochodów"
- W_n(S_n) - kwalifikator - może być sumaryzatorem (lub ich koniunkcją?) - np. "Duży silnik"
- S_n - koniunkcja sumaryzatorów - np. "Współczesny" i "Ekonomiczny"

### Wielopodmiotowe w pierwszej formie

**Q P_n compared to P_m are/have S_n**

- Q - kwantyfikator (jaki?) - np. "all", "some", "no"
- P_n - podmiot - "samochodów z dieslem"
- P_m - podmiot - "samochodów z silnikiem benzynowym"
- S_n - koniunkcja sumaryzatorów - np. "Duży silnik" i "Ekonomiczny"

$M_1 + M_2 = M$,  
$T = \mu_q (\frac{\frac{1}{M_1} * card(S(P_1))}{\frac{1}{M_1} * card(S(P_1)) + \frac{1}{M_2} * card(S(P_2))})$

### Wielopodmiotowe w drugiej formie

**Q P_n compared to P_m being/having W_n(S_n) are/have S_n**

- Q - kwantyfikator (jaki?) - np. "all", "some", "no"
- P_n - podmiot - "samochodów z dieslem"
- P_m - podmiot - "samochodów z silnikiem benzynowym"
- W_n(S_n) - kwalifikator - może być sumaryzatorem (lub ich koniunkcją?) - np. "Duży silnik"
- S_n - koniunkcja sumaryzatorów - np. "Współczesny" i "Ekonomiczny"

$T = \mu_q (\frac{\frac{1}{M_1} * card(S(P_1))}{\frac{1}{M_1} * card(S(P_1)) + \frac{1}{M_2} * card(S(P_2)\cap W)})$

### Wielopodmiotowe w trzeciej formie

**Q P_n being/having W_n(S_n) compared to P_m are/have S_n**

- Q - kwantyfikator (jaki?) - np. "all", "some", "no"
- P_n - podmiot - "samochodów z dieslem"
- P_m - podmiot - "samochodów z silnikiem benzynowym"
- W_n(S_n) - kwalifikator - może być sumaryzatorem (lub ich koniunkcją?) - np. "Duży silnik"
- S_n - koniunkcja sumaryzatorów - np. "Współczesny" i "Ekonomiczny"

$T = \mu_q (\frac{\frac{1}{M_1} * card(S(P_1)\cap W)}{\frac{1}{M_1} * card(S(P_1)\cap W) + \frac{1}{M_2} * card(S(P_2))})$

### Wielopodmiotowe w czwartej formie

** More P_n than P_m are/have S_n**

- P_n - podmiot - "samochodów z dieslem"
- P_m - podmiot - "samochodów z silnikiem benzynowym"
- S_n - koniunkcja sumaryzatorów - np. "Duży silnik" i "Ekonomiczny"

$T = 1 - m(Inc(S(P_2), S(P_1)))$  
Inc - inkluzja, zawieranie się, pewien zbiór rozmyty  
m - miara rozmyta (względna) zbioru (1.11 pl)  
$A \subseteq B \rightarrow m(A) \leq m(B)$  
$\mu_{inc(A,B)}(x) = I(\mu_A(x), \mu_B(x))$  
implikacja Łukasiewicza  
$I(a,b) = min\{1, 1-a+b\}$  
implikacja Reichenbacha  
$I(a,b) = 1 - a + ab$  

## Miary jakości podsumowań lingwistycznych

wyrażone liczbami rzeczywistymi z przdziału [0,1] - im bliżej 1, tym podsumowanie jest lepsze

### Dla podsumowań jednopodmiotowych

**degree of truth** (poprzedni wykład) -> $T_1$  
_degree of truth_ - można użyć dowolnego kwantyfikatora  
$T(Q \space objects \space are \space S_1) = \mu_Q (\frac{\sum{-count}(S_1)}{M}), M = 1$

tu tylko relatywny kwantyfikator (pewna liczba tych obiektów które mają jakąś cechę)  
$T(Q \space objects \space being \space S_2 \space are \space S_1) = \mu_Q (\frac{\sum{-count}(S_1 \cap S_2 )}{\sum{-count}(S_2)})$

- licznik - kardynalność części wspólnej kwalifikatorów i sumaryzatorów
- mianownik - kardynalność kwalifikatorów ?

**degree of impecision $T_2$ (stopeiń precyzyjności/nieprecyzyjności)**  
$T_2 = 1 - (\prod^n_{j=1} in(S_j))^\frac{1}{n}$  
wz 8.42, przyk 8.6  
in(s1) = 0.5, in(s2) = 0.5  
$T_2 = 1 - \sqrt{(0.5 * 0.5)}$  
średnia geometryczna (?? )  
nie zależy od kwantyfikatora Q  
czy kwalifikator też?

**degree of convering $T_3$ (stopień pokrycia)**  
wz 8.44  
funkcja przynależności do nośnika zbioru rozmytego W (kwalifikatora) wz 8.46  
funkcja przynleżności do nośnika części wspólnej zbiorów S i W, wz. 8.45  
$T_3 = \frac{|supp(S\cap W)}{|supp(W)|}$  
wartość rozpatrujemy dla zbioru rekordów (skończona liczba, naturalna)  
jeżeli stopień pokrycia jest bliski 1, znaczy to że część wspólna S i W jest zbliżona rozmiarem do W (duża liczba elementów należących do W może być opisana przez S)  
jeżeli nie ma kwalifikatora W (podsumowanie w 1szej formie), można przyjąć domyślny kwalifikator (wszystkie obiekty KTÓRE SĄ W BAZIE...) wz. 8.49  
dla każdego zbioru klasycznego A supp(A) = A  
miara $T_3$ nie zależy od kwantyfikatora Q (miara $T_1$ już zależy)

**degree of appropriateness $T_4$ (stopień trafności)**  
wz 8.53   
liczymy $r_j$ osobno dla sumaryzatorów
0 wyjdzie dla każdego podsumowania z jednym sumaryzatorem

**length of summary $T_5$ (długość podsumowania)**  
wz. 8.57  
podsumowanie jest tym gorsze im więcej ma sumaryzatorów

kolejne miary ->

**degree of quantifier imprecision $T_6$ (stopien niedokładności kwantyfikatora)**  
im węższy nośnik kwantyfikatora, tym jest on lepszy
niezależna od sumaryzatora czy kwalifikatora  
zależne od "modelu" a nie bazy danyh

**degree of quantifier cardinality $T_7$ (stopień kardynalności kwantyfikatora)**  
wz. 8.67  
pole pod wykresem kwantyfikatora  
im większe pole, tym gorszy kwantyfikator

**degree of summarizer cardinality $T_8$ (stopień kardynalności sumaryzatora)**  
wz 8.69
liczba kardynalna sumaryzatora (nie jego nośnika)  
dla konkretnego zbioru rekordów (dla bazy danych)

**degree of qualifier imprecision $T_9$**

**degree of qualifier cardinality $T_{10}$**

**degree... $T_{11}$**  
im więcej zbiorów rozmytych w kwalifikatorze, tym gorzej dla podsumowania  
w polskiej książce

WZ 8.75  
średnia ważona ze wszystkich miar  
suma wag musi = 1
(? odpowiada na pytanie "które podsumwanie jest najbardziej wartościowe")  
wynikiem jest podsumowanie lingwistyczne dla którego maksymalizuje się średnia

### Dla podsumowań wielopodmiotowych