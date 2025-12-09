# Rules

- Always use CommandAPI to create all commands.
- Always use the MessageUtils class and methods to create messages for the plugin.
- All messages are in Minimessage format (sendMessage, sendConsoleMessage, etc).
- All items will be built with the ItemBuilder class.
- All sounds will be added with the SoundUtils class.
- Use Java 21 to develop.
- Always use SpigotMC to create all listeners.
- All commands will be registered in the CommandManager class, and the class command will be in the Command package.
- All events will be registered in the EventManager class, and the class event will be in the Event package.
- All listeners will be registered in the ListenerManager class, and the class listener will be in the Listener package.
- All configs will be registered in the ConfigManager class, and the class config will be in the Config package.
- All messages will be in the messages.yml file, and the class message will be in the MessageManager class. The method to read component message string will be by keys like this "message.messagename".
- Use Lombok annotations to make your code more readable (@setter, @getter, etc).
- Do not modify any utility classes from "com.spectrasonic.utils.*" as these classes work correctly and do not require any modification.


- Do not compile the project at least i ask for it
- Do not write any javadocs comments, only line comments, and keep it simple

- Use MCP context7 to see the docs