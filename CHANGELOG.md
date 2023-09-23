Created the DependencyInformation class
- Needed in order to extract all the public methods and additional fields from Ai Player in order 
- to maintain the integrity of the interface implementation

Created the AiPlayerDependency class
- Needed in order to have a class that allows for dependency injection to be used so we can modify 
- information in our AiPlayer model without having to have to make any unnecessary methods

Updated the AiController to use AiPlayerDependency instead of AiPlayer
- This allows for us to have a much cleaner implementation so we don't have to rely on passing in 
- the entire Human Player Model in order to run our game

Updated AiPlayer to implement interface methods
- Implemented stable working implementations of the methods in the interface in order to make
- communication with the server possible and for better design

Updated HumanController to use Player interface methods
- The updateShots method now relies on the interface methods instead of relying on the
- heaps of information given to it in the previous design