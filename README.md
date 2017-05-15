# Simple-AppUpdaterJSON-

FR - US

# Important : 

Ce projet n'est pas une librairie !


# Informations : 

Ce projet vous montre comment ajoutée une fonctionnalité de mise à jour à votre application qui ne se trouve pas sur le Google Play Store

Ce projet vous montre comment le faire vous-même, sachez que la méthode à suivre est vraiment très simple (moi-même je me suis demandée pourquoi je ne y avais pas penser plutôt)


# Utilisation : 

Utilisation du JSON
Utilisation d'un convertisseur de données String à Int
Utilisation de la state IF et ELSE
Utilisation d'un Gestionnaire de téléchargement


# Explications : 

En quelques phrases... 
J'utilise un gestionnaire de téléchargement pour télécharger un fichier JSON, puis l'application va le lire et prendre les données dont je ai besoins, ensuite j'exploite ces informations.

VersionCode est le plus important, car l'application va lire l'objet VersionCode est prendre sont texte (qui doit êtres un numéro) , ensuite je récupère moi-même ce texte puis je doit utiliser une state IF et ELSE pour exécuté deux méthode différentes, mais avant je doit convertir le texte de VersionCode et le convertir en Int, une fois la conversation faite j'utilise la state IF et ELSE en le utilisant comme un comparateur de numéro, la valeur plus grande va exécuté une méthode et la valeur plus petite va exécuté un autre méthode.

Les numéros VersionCode seront noté dans le fichier JSON et le VersionCode de votre application.

C'est ainsi que la fonctionnalité de mise à jour ce fait (elle fonctionne très bien), ceci est une méthode simple à vous d'en modifier les propriétés pour les utilisés à votre compte.



# Important:

This project is not a bookstore!


# Information:

This project shows you how to add an update feature to your app that is not on the Google Play Store

This project shows you how to do it yourself, know that the method to follow is really very simple (myself I wondered why I did not think about it)


# Use :

Using the JSON
Using a String to Int Data Converter
Using the state IF and ELSE
Using a Download Manager


# Explanations:

In a few sentences ...
I use a download manager to download a JSON file, then the application will read it and take the data I need, then I use this information.

VersionCode is most important, because the application will read the VersionCode object is take text (which must be a number), then I retrieve this text myself then I have to use a state IF and ELSE to run two different method , But before I have to convert VersionCode text and convert it to Int, once the conversation is done I use the IF and ELSE using it as a number comparator, the larger value will run a method and the smaller value Will run another method.

The VersionCode number will be noted in the JSON file and the VersionCode of your application.

This is how the update feature does this (it works fine), this is a simple method for you to change the properties for used to your account.
