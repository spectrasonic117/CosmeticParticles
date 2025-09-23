# Code Style

Always use CommandAPI to Create all comands.

Always MessageUtils class and methods, to create the messages for the plugin, all messages are in Minimessage Format (sendMessage, sendConsoleMessage, etc)
All Items will build with the ItemBuilder class.
All sounds will be added with the SoundUtils class.

Use Java 21 to develop
Always use SpigotMC to Create all Listeners.

All Commands will be registred in the CommandManager class, and the classs command will be in the Command Package.
All Events will be registred in the EventManager class, and the class event will be in the Event Package.
All Listeners will be registred in the ListenerManager class, and the class listener will be in the Listener Package.
All Configs will be registred in the ConfigManager class, and the class config will be in the Config Package.

All messages will be in messages.yml file. and the class message will be in the MessageManager class. and always use the Minimessage, the metod to read component message string will be by keys like this "message.messagename".

Use Lombok Annotations, to make your code more readable. (@setter, @getter, etc)

Do not modify any utility classes from “com.spectrasonic.utils.*” as these classes work correctly and do not require any modification.

# Search Documentation

Use Context7 to browse Documentation of dependencies:
    - SpigotMC
    - PaperMC
    - MiniMessage 
    - CommandAPI
    - Java 21

# Never Do

Do not delete .git file
Do not print or modify pom.xml file, unless I ask you to