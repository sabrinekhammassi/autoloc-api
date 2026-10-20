# Note sur la couche Service — Atelier 4

## A. Modes d’injection comparés

| Mode | Champ final | Dépendance visible | Utilisable avec `new` sans dépendance nulle | SonarQube |
|---|---|---|---|---|
| Constructeur (`@RequiredArgsConstructor`) | Oui | Oui, dans le constructeur généré | Oui, si le repository est fourni | Recommandé |
| Attribut (`@Autowired`) | Non | Non | Non, champ nul hors conteneur | S6813 |

Les services utilisent des champs `final` et une injection par constructeur générée par Lombok. Ils n’ont aucun état propre à une requête.

## B. Services

| Service | Dépendance | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Dépendance obligatoire typée par interface |
| ClientServiceImpl | IClientRepository | Constructeur | Dépendance obligatoire typée par interface |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Dépendance obligatoire typée par interface |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Dépendance obligatoire typée par interface |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Dépendance obligatoire typée par interface |
| ReservationServiceImpl | IReservationRepository | Constructeur | Dépendance obligatoire typée par interface |
| ContratServiceImpl | IContratRepository | Constructeur | Dépendance obligatoire typée par interface |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule ; les paiements sont gérés par le contrat |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Dépendance obligatoire typée par interface |

## C. Messages d’erreur Spring

1. `NoSuchBeanDefinitionException` pour `IContratService` : `ContratServiceImpl` peut ne pas porter `@Service`, ou se trouver hors du package scanné. Ajouter l’annotation et conserver la classe sous `tn.esprit.autoloc`.
2. `NoUniqueBeanDefinitionException` : ajouter `@Primary` sur l’implémentation par défaut, ou `@Qualifier("emailNotificateur")` sur le paramètre concerné du constructeur.
3. Cycle entre `ClientServiceImpl` et `ReservationServiceImpl` : chaque constructeur exige l’autre bean avant de pouvoir créer le premier. Extraire le traitement partagé dans un troisième service, puis faire dépendre les deux services de cette abstraction.

## D. Anomalies SonarQube for IDE

| Règle/anomalie | Explication | Correction |
|---|---|---|
| S6813 | Injection directe dans un champ | Injection constructeur avec champs `final` et `@RequiredArgsConstructor` |
| S112 | Exception trop générique | Utilisation de `ResourceNotFoundException` pour une ressource absente |
| Création avec identifiant préexistant | `save` pourrait faire un merge | Refus explicite de tout identifiant non nul dans `create` |

## E. Questions de compréhension

1. Spring crée `ContratServiceImpl` pendant l’initialisation du contexte à partir du constructeur Lombok généré et lui fournit le proxy de `IContratRepository`.
2. Le contrôleur dépend de `IContratService` pour rester découplé de l’implémentation et faciliter son remplacement ou son test.
3. Un singleton sert plusieurs threads. Un attribut modifiable tel qu’un « contrat courant » pourrait être écrasé entre deux requêtes et retourner le contrat d’un autre utilisateur.
4. `update` charge l’entité gérée puis recopie les champs autorisés. Cela évite l’écrasement de champs omis, de l’identifiant et de collections avec `orphanRemoval`.
5. `@Component` marque une classe détectable par scan ; `@Bean` déclare explicitement le résultat d’une méthode de configuration. `@Primary` choisit le bean par défaut ; `@Qualifier` sélectionne un bean à un point d’injection précis. `IPaiementService` est en lecture seule car création, modification et suppression passent par le contrat et sa cascade de paiements.
