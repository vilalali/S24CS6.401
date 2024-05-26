LECTURE-II

Software Modeling:
An Overview
Let us consider a real system
Let us now consider a model of the system
"The brain does much more than recollect. It compares, synthesizes, analyzes, generates abstractions.”
Model is a simplification of a reality. In other words, it is a blueprint of the system
Modelling can serve more purpose

So what is a software model?
A simplified or partial representation of a real system, defined in order to accomplish a task or to reach an agreement.

Mapping: A model is always a mapping of some real system Reduction: A model reflects only relevant set of properties of original system Pragmatism: A model needs to be usable in place of the actual system with respect to some purpose.

Quality of Model
As per Bran Selic, five characteristics determine model’s quality 
• Abstraction: A model should be reduced version of system [Omit unwanted]
• Understandability: Should be as intuitive as possible
• Accuracy: Reflect relevant properties as close to reality as possible
• Predictiveness: Enable prediction of interesting properties of system
• Cost-effectiveness: Cheaper to create models than the system
Glimpse into world of Model-driven Engineering
Model Driven Software Engineering
Shifting focus from code centric techniques to models

What is MDE? – Key Motivation
Models as a sketch
• Communication of ideas
• Objective: Modeling per se 

Models as guidelines/blueprint
• Design decisions are documented
• Objective: instrumentation for implementation

Models as executable programs
• Generate code automatically
• Objective: models are source code and vice versa

What to Model?
Multiple ways to think about it
Algorithmic Perspective
• Main block of building the software is procedure or function
• Scale and new features affects maintainability and reasoning

Object oriented Perspective
• Main building block of all software system is object or class
• Contemporary view of software development

Object Oriented Modeling
• Model system as a collection of objects that interact with each other
• Tries to captures the real-world scenario
• Everything is an object
• Has a state and behavior: (Happy, angry)…(speaking softly, yelling…)
• Can you model a person?
• Software objects are similar to real world objects:
• Store state in fields (variables)
• Behavior through methods (functions)

Objects vs Classes
Real world
Modeling World
ObjectObject represents anything
An object has identity, state and
that can be distinctly identified behavior
ClassRepresents set of objects
with similar characteristics
and behavior
Object is like a variable of a class
It characterizes
the structure of states and
behaviors shared by its
instances.

Key Characteristics
• Abstraction: Hide irrelevant details (eg: Coffee machine)
• Deals with What!
• Encapsulation: Protection against unauthorized access (eg: Organization)
• Deals with how!
• Relationships
• Inheritance: Derive classes from existing classes (eg: Real life inheritance!!)
• Association: Relation between two classes (Aggregation, composition)
• Dependency: Some form of dependency between two classes

How to Model?
How do you interpret this?

Can you create a model?
Think of a course management system like moodle. Can you create a model
for the same?
Just use whatever knowledge you have, any type of diagram as per your
knowledge is fine – Give a try!!

LECTURE-III
Modeling Languages
Modeling Languages
Largely classified into two types
• Domain-Specific Languages (DSLs)
• Languages designed to model a certain domain
• Examples: HTML, SQL, etc.
• General Purpose Modeling Languages (GPLs)
• Languages can be applied to any domain for modeling
• Examples: UML, XML, etc.

In the Context of Our Course
(GPL – UML)
Unified Modeling Language (UML): Brief History
• No common language to model until 1996
• GPL developed by industry consortium in 1997
• Introduction of OOP in IT dates back to 1960’s
• Required a standard representation: OMG
• Three Amigos: Grady Booch, Ivar Jacobson and James Rumbaugh
• Based on multiple prior visual modeling languages
• Goal was to have a single language that could cover large number of SE tasks
• Current version of UML: 2.5.1 (as of Dec 2017)

Unified Modeling Language (UML)
• Notation for OO Modeling
• Use object orientation as basis
• Model a system as collection of objects that interact with each other
• Graphical diagrams as a way to model systems
• More clear (imprecise) than natural language (too detailed)
• Capture an overall view of the system
• Independent of language or technology

What UML is not?
• Not an OO Method or Process
• Not a visual programming language
• Not a tool specification

UML Diagrams
• 14 different diagrams
• Structure diagrams for capturing static aspects of system
• Behavior diagrams for capturing dynamic aspect of system

Static Vs Dynamic Models
Static Model
• Describes the static structure of a system
• One of the most common diagrams: class diagrams Dynamic Model
• Captures the dynamic behavior of a system
• Developed with help of state chart diagrams, sequence diagrams, etc.
In this unit: class diagram (static) and sequence diagram (dynamic)

UML Class Diagram
• Most common diagram in OO modeling
• Captures the static structure of a system
• Intuitively it is like a graph
• Nodes represent the classes
• Links represent the relationship among classes
• Inheritance
• Association (aggregation, composition)
• Dependency

UML Class Diagram: Notation
Consists of three compartments
Class name - Pascal Casing, Singular noun, domain vocabulary
Fields/Attributes (state) - camel casing, name and type at basic level
Methods/operations (behavior) – camel casing, name, parameters, return value

UML Class Diagram: Always make use of abstraction
• Model has to be clear and understandable
• Detail with respect to the stage of software development process
• More low-level analysis and development requires detailed information

UML Class Diagram: Specifying Attributes and Methods
Name and SymbolDescription
public (+)Access by objects of any class
Private (-)Access only within the object
Protected (#)Access by objects of same
classes or sub-classes
Package (~)Access by objects of the
classes which are in same
package

Create a class diagram for the following code
Interface and Notation for Interfaces
• In simple terms it’s a contract mechanism – What to do!
• Mechanism to achieve abstraction, group classes, enforcer – No
instance variables only constants
• Class can implement an interface – “implements” keyword (Java)
• Vehicles can implement Gear interface

Notation for Objects
• Box with one or two compartments
• Remember to mention the class name
First part has object name and corresponding class name
Second part has list of fields and values
Models and Meta models
• Models of models
• Defines the rules for the different models
• For eg: a class needs to be defined in a particular way
Modeling Relationships using UML

Three main relationships between classes
• Dependency
• Class A uses Class B
• Associations (has-a)
• Class A affects Class B
• Types: Aggregation and Composition
• Generalization (Is-a)
• Class A is a kind of Class B

Inheritance in Java
• Object acquires properties and behavior of parent object
• Create new classes based on existing classes
• Derive classes from existing classes (”extends” keyword)
• Parent class/super class – Class from which other classes are derived
• Child class/sub class – Class that is derived from existing class
• Object class is the parent class for every class in java (java.lang.package)
• Eg: Vehicle class can be parent of car, bikes, etc.
• Each car, bike can themselves be parent class for child classes – How?

Inheritance in UML
• UML provides easy ways to represent inheritance
• Extension is called specialization (sub class) and generalization (supper class)
• Implementation is called realization
More Concrete Example

Time to be Creative
Draw a UML diagram showing possible inheritance relationship between different types of students in the class. What will be the abstract class (es)? Hint: We have B.Tech, M.Tech, …..

Association
• Model links between instances of classes
• Identify the communication partners
• Use association names and reading directions (solid arrowhead)
for labeling
Professors gives lecture
What kind?
What about multiplicity?
28Association – Navigability and Multiplicity
• Cardinality of the class in relation to the another - Multiplicity
• Navigation from one to another is possible – Navigability
• Navigability - Indicates who can access what (not reading direction)
• Usual assumption: Bidirectional navigability
Bidirectional
Professor class cannot access public
parameters/methods of student
29Association – Few more things
• May have optional role name
• Multiplicity specification is not always mandatory
• min…max: closed (inclusive) range of integers
• n: single integer
• 0..*: entire set of non-negative integers
30Aggregation
• Special form of association - Parts-whole relationship
• Used to express that a class is part of another (hollow diamond)
• Combination of independent objects (eg: Program and course)
Represented by a hollow diamond
Another example
31Composition
• Dependency between composite objects and its parts
• If the composite object is deleted, the parts are also deleted
• One part can be contained in at most one composite object at a time
• Max multiplicity at the aggregating end is 1 (closed diamond representation)
Building is composed of multiple rooms
Adding centers from Institute
32Dependency
• One class uses another class <<uses> relationship
• There is no conceptual link between the objects of the classes
• One may refer the other or vice versa
33Time to be Creative
Let’s revisit the case this time with class diagrams:
we want to build a course management portal (think of
moodle), what could be some of the classes the
corresponding attributes and methods? Can you think of
some interfaces?
Modeling the Dynamic
Aspects: Sequence Diagram
[Interaction Diagram]
Sequence Diagram
• Captures the dynamic behavior
• Two dimensional-diagram
• Horizontal: Involved interaction
• Vertical: Chronological order of the interaction
• Interaction => sequence of event specifications
Sequence Diagram – Main Message types
• Synchronous Message
• Sender waits till the return message is received before next
• Asynchronous Messages
• Sender does not wait for response message
• Response message
• Not mandatory in obvious situations

Sequence Diagram – Combined Fragments
• Model control structures explicitly
• UML sequence diagram supports 12 operators. Three groups
• Branches and loops, Concurrency and order, Filters and Assertions

Different Operators
Name and OperatorUse
Alternative (alt)Express alternative execution (if-else)
Optional (opt)Fragment executes based on guard condition (if)
Break (break)Execution of a fragment when break condition is met
Loop (loop)Repeated execution of a fragment
Sequential (seq)Weak ordering (default model)
Strict (strict)Interaction with strict order
Parallel (par)Concurrent execution of sub-scenarios
Critical (critical)Atomic interactions (no other interactions can affect)
Ignore (ignore)Irrelevant messages (insignificant messages at runtime)
Consider (consider)Important messages of the interactions
Negate (neg)Model invalid interactions, undesirable situations
Assert (assert)To assert certain interactions (mandatory)

LECTURE-V
Refactoring: An Introduction Ever heard about Technical Debt?
Technical Debt - Definition
Technical debt is the debt that accrues when you knowingly or
unknowingly make wrong or non-optimal design decisions
Metaphor coined by Ward Cunningham, 1992
Types of Technical Debt
Design Stamina Hypothesis
Impact of Technical Debt
“One large North American bank learned that its more than 1,000 systems and
applications together generated over $2 billion in tech-debt costs” - McKinsey
• Interest is very much compounding in nature – changes has to be done
on already existing debt
• Cost of Change becomes extremely high!
• Affects morale of development team
• Huge impact on progress of the business – product and feature delays
• Often considered as the digital dark matter!

Impact of Technical Debt – An Example Scenario
A successful company in the maritime equipment industry successfully evolved its
products for 16 years, in the process amassing 3 million lines of code. Over these 16
years, the company launched many different products, all under warranty or
maintenance contracts; new technologies evolved; staff turned over; and new
competitors entered the industry.
The company's products were hard to evolve. Small changes or additions led to large
amounts of work in regression testing with the existing products, and much of the
testing had to be done manually, over several days per release. Small changes often
broke the code, for reasons unsuspected by the new members of the development
team, because many of the design and program choices were not documented.
What were some things they could have done right?

Impact of Technical Debt – Another Case
Reasons for Technical Debt
Everyone in the decision making could be blamed – Architects, developers, managers..
but that doesn’t end there. There are many other reasons..
• Schedule pressure – Copy paste programming
• Its not always about getting the syntax right and making something work
• Lack of skilled designers – Poor applications of design principles
• Lack of awareness about best practices
• Leading in the wrong direction
• Lack of awareness of key indicators and refactoring - Design issues
• Periodic review of design and making changes can go a long way!
Managing Technical Debt
• Increase awareness about tech debt
• Being aware is the best start
• Create goals keeping this in mind
• Detect and repay tech debt systematically
• Identify instances of debt (huge impact)
• Create systematic plan on recovery
• Prevent accumulation of tech debt
• Once under control, prevent further accumulation
• Perform regular monitoring
• Companies should allocate some budget for tech debt 17 Image source: xkcdKey Major Questions
1. Why do even good developers write bad software?
2. How do we fix our software?
3. How to know if the software is “bad” even when its working fine?

Refactoring!
“Any fool can write code that a computer can understand. Good Programmers write code that humans can understand”

What is Refactoring?
It is a change made to the internal structure of software to make it easier to understand and cheaper to modify without changing its observable behaviour -- Martin Fowler

What is Refactoring?
• Refactoring is not always a clean up of code!
• Goal is to make software easier to understand and modify
• Think of performance optimization
• Refactoring does not or should not change behavior – No change to external user [Changing hats]
• Not always same as:
• Adding features
• Debugging code
• Rewriting code

When to Refactor?
• Follow the rule of three
• First time, just get it done
• Second time to do something similar, duplicate
• Third time, just refactor
• Refactor when you add a function (feature)
• When adding new feature, make it more effective and efficient
• Refactor when you fix a bug
• Bug by themselves can be good indicators – Are they becoming more common?
• Refactor when you do code reviews
• Create review groups for code reviews, new perspective may lead to refactoring

Some Common Refactoring – Low Level refactoring
• IDEs provide a lot of support
• Variable/method/class renaming
• Extraction of duplicate code snippets
• Change in method signature
• Method or constant extraction
• Warnings about unused variables, parameter uses/declarations
• Auto-completion support and minimal documentation support

High-level refactoring - Challenges
• Much more complex – has dependency on use case, context
• Risk of introducing bugs – Changes in design can introduce new issues
• Testing can become difficult – New test cases needs to be added, overall Behavior may change [ideally not!]
• Communication of changes – Changes can be more abstract and harder to explain
• Measuring the impact – Changes can be harder to quantify

Design Smells and Refactoring
How to Identify Technical Debts and Refactor?
Software Quality as an Indicator
How to Refactor?
• Identify the refactoring points
• Create a refactoring plan
• Make a backup of the existing codebase: Versioning system
• Use semi-automated approach: Some tool support is always available
• Perform the refactoring
• Test if everything works like before! – Test extensively (new bugs, broken functionalities, etc.)
• Repeat the process 
Remember: Refactoring is not just a one time activity!!

Code Smells? You heard that right!
Refactoring Points - Things starts to rot and Smell
Code Smells and so does design – You heard that right!!!
”smell”, Coined by Kent Beck in 1999
Smells are certain structures in the code that suggest (sometimes they scream for) the possibility of refactoring
A ”bad smell" describes a situation where there are hints that suggest there can be a design problem
Types of Design Smells
Missing Abstraction – Example Scenario
Scenario: Consider the e-bike system which requires to store address of every user

Missing Abstraction – Example Refactoring
Solution: Refactor the design, move collection of primitive types and form a separate class

Abstraction Smell – Missing Abstraction
Indication: Usage of clumps of data or strings used instead of class or interface
Rationale: Abstraction not identified and represented as primitive types
Causes: Inadequate design analysis, lack of refactoring, focus on minor performance gains
Impact: Affects understandability, extensibility, reusability, .

Abstraction Smell – Imperative Abstraction
Scenario: Consider the e-bike system where students have to perform different operations on their wallet

Abstraction Smell – Example Refactoring
Solution: Refactor the design, move the functions into one class and bundle it with data
Remember abstraction is all about generalization
And specification of common and important characteristics!!

Abstraction Smell – Imperative Abstraction
Indication: Operation is turned into a class. A class that has only one
method defined in it
Rationale: Defining functions explicitly as classes when data is located
somewhere violates OOPS principles. Increases complexity, reduce cohesiveness Causes: Procedural thinking (capture the bundled nature)
Impact: Affects understandability, extensibility, testability, reusability..

Abstraction - Enablers
• Crisp boundary and identity
• Make abstractions when necessary and have clear boundaries
• Map domain entities
• Vocabulary mapping from problem domain to solution domain
• Ensure coherence and completeness
• Completely support a responsibility, don’t spread across
• Assign Single and Meaningful Responsibility
• Each abstraction has unique and non-trivial responsibility
• Avoid Duplication
• The abstraction implementation and the name appears only once in design

Encapsulation Smell – Deficient Encapsulation
Scenario: Consider the e-bike system where user details like DOB, gender, etc. are public
Encapsulation Smell – Example Refactoring
Solution: Refactor the design, modify the access specifiers without affecting others

Encapsulation Smell – Deficient Encapsulation
Indication: One or more members is not having required protection
(eg: public)
Rationale: Exposing details can lead to undesirable coupling. Each change in abstraction can cause change in dependent members 
Causes: Easier testability, procedural thinking (expose data as global variables), quick fixes
Impact: Affects changeability, extensibility, reliability,…

Encapsulation Smells – Leaky Encapsulations
Scenario: Consider the e-bike system where the docking station class provides list of bikes parked in that station
Encapsulation Smell – Example Refactoring
Solution: Refactor the design, make return types of public more abstract to support modifiability, ensure clients do not get direct access to change internal state

Encapsulation Smells – Leaky Encapsulations
Indication: Abstraction leaks implementation details (public methods) 
Rationale: Implementation details needs to be hidden, Internal state can be corrupted due to open methods
Causes: lack of awareness, project pressure (quick hacks), too fine- grained public methods exposed (think of simple setter)
Impact: Affects changeability, reusability, Reliability

Encapsulation - Enablers
• Hide implementation details
• Abstraction exposes only what abstraction offers and hides implementation
• Hide data members and details on how the functionality is implemented
• Hide Variations
• Hide implementation variations in types or hierarchies
• Easier to make changes in abstraction implementation without affecting subclasses or collaborators

Modularization Smells – Broken Modularization
Scenario: Bike class gets all data from BikeDetails class but all operations resides in Bike Class

Modularization Smells – Example Refactoring
Solution: Refactor the design in such a way that the data and methods stay together as a unit. Enhancing cohesiveness is the key

Modularization Smells – Broken Modularization
Indication: Data and methods are spread across instead of being bundled 
Rationale: Having data in one and methods in another results in tight coupling, violates modularity
Causes: Procedural thinking, lack of understanding of existing design 
Impact: Affects changeability and extensibility, reusability, Reliability

Modularization Smells – Enablers
• Localize related data and methods
• All the data and method related to one class should be kept in the same class
• Abstractions should of manageable size
• Ensure classes are of manageable size – mainly affects maintainability, extensibility and understandability
• Ensure there are no cyclic dependencies
• Graph of relationships between classes should be acyclic
• Limit Dependencies
• Create classes with low fan-in and low fan out
• Fan-in: number of incoming dependencies
• Fan-out: number of outgoing dependencies

Hierarchy Smells – Missing Hierarchy
Scenario: In the e-vehicle scenario, user can pay in any mode of payment
One way to support different types of payment is to write them inside makePayment function

Hierarchy Smells – Example Refactoring
Solution: Refactor by creating hierarchies based on the behavior changes that comes under payment function. Put the common parts in parent class (think about abstract class or interfaces as well)

Hierarchy smells – Missing Hierarchy
Indication: Using if conditions to manage behavior variations instead of creating hierarchy 
Rationale: Using chained if-else or Switch indicates issues with handling variations. Commonality among the types can also be used
Causes: ”simplistic design”, procedural approach, overlooking inheritance 
Impact: Reliability, Testability, understandability, extensibility,..

Hierarchy smells – Example Scenario
Scenario: Each bike can be of different model resulting in different design (shape, colour, etc.)

Hierarchy smells – Refactoring
Solution: Remove hierarchy and transform subtypes into instance variables

Hierarchy smells – Unnecessary Hierarchy
Indication: Inheritance has been applied needlessly for a particular context
Rationale: The focus should be more on capturing commonalities and variation in behavior than data. Violation results in unnecessary hierarchy
Causes: subclassing instead of instantiating, taxonomy mania (overuse of inheritance)
Impact: Understandability, Extensibility, Testability..

Hierarchy Smells - Enablers
• Apply meaningful classification
• Identify commonalities and variations – Classify into levels
• Apply meaningful generalization
• Identify common behavior and elements to form supertypes
• Ensure Substitutability
• Reference of supertype can be substituted with objects of subtypes
• Avoid redundant paths
• Avoid redundant paths in inheritance hierarchy
• Ensure proper ordering
• Express relationships in a consistent and orderly manner

Some General Observations
• Analyze your design
• Is this abstraction enough?
• Is there some responsibility overload?
• Have we made use of the right set of access modifiers?
• Only expose what is necessary
• Ensure high cohesiveness and loose coupling
• Create hierarchies whenever necessary (only when necessary)
• Always remember, refactoring is not a one-time process
• The more it is delayed, the more debt is incurred!
• Combination of design smells exists
• Code can serve as good indicators of design smells – Code also smells!

Next up: Code Smells and Code Metrics!!
Group Activity

LECTURE-7
Mining Software Repositories
The MSR field analyzes rich data available in software repositories to extract useful and actionable information about software projects and systems – msrconf.org
• Large amount of artefacts are generated in the software development process
• These data are available from various sources
• Version control systems (SVN, Mercurial,..)
• Q&A Forums (Stackoverflow, Stackexchange, etc.)
• Bug repositories (BugZilla, Jira)
• Code repositories (Github, Gitlab, etc.)
• Crash reports, log files, execution traces, etc.

Mining Software Repositories
Repositories are great sources of unbiased data on how a product came to be - something that's very valuable and hard to find.
-- Andreas Zeller
• Generate insights on best practices (eg: Sources of technical debt)
• Develop approaches for automated code completions, bug localization,..

But what about code?
Code can also Smell
Code smell serves an indication that there is deeper problem in the system
• Code smells are only hints – not necessarily a problem!
• Look for bad smells – definitely needs a refactoring!
• But is there some list that could be used? Or common ones?
Some Examples

Code Smells – Long Method
Expand the comments to code – that’s a very long method!!
Long Method - Refactoring
What changes do you notice? – Any comments ? Can we do better?
Code Smells - Long Method
Longer a procedure, more difficult it is to understand
Context: A method has to perform a series of computations to accomplish a functionality
Problem: All the computations are written in its entirety inside one method making
the method long - Too many lines of code!!
Suggested Refactoring: Any method longer than 20 lines (even 10+) is worth inspecting
Decompose the method into smaller methods -> Extract Method 
This may result in other smells, which can call for further refactoring – parameter list

Code Smells – Long Parameter List
See the number of parameters that are taken as input by enrolmentHandler

Long Parameter List – Refactoring
The parameters are now respective objects, can we do further?
Code Smells - Long Parameter List
Try to have not more than 4 parameters – Not a Golden rule
Context: A method has to perform a series of computations to accomplish a functionality and it takes in lot of parameters
Problem: Hard to understand and the calling function/method needs to place the parameters in right positions, attracts adding of even more parameters!!
Suggested Refactoring: Multiple ways
• Replace parameter with method (call inside)
• Preserve whole object
• Introduce parameter object (if data items are related and no logical object exists
Long parameter can indicate other smells (eg: long methods, data clumps, primitive obsession)
Code Smells – Primitive Obsession
Overuse of primitive types
Primitive Obsession – Refactoring
We can do further, can we?

Code Smells – Primitive Obsession
Over use of primitive data types instead of objects
Context: A method has to perform a series of computations to accomplish a functionality and it takes in lot of parameters of primitive types Problem: Having too many primitive types may lead to long parameters and can contribute to code duplication and type mismatches
Suggested Refactoring: Multiple ways
• Replace data value with object
• If there are group of fields (extract class)
• If there are fields that belong to object (Introduce parameter object)
Primitive obsession can lead to other smells (eg: long methods, data clumps, long Methods, etc.)
17Code Smells – Switch Statements (Conditional Complexity)

Too many conditions!! Can we do better?
Switch Statements (Conditional Complexity) - Refactoring
Leveraging inheritance and polymorphism, we can do this for different types of student

Code Smells – Conditional Complexity
Complex set of switch or sequence of if conditions. When nesting goes too far!!
Context: A Single class has some operations and it requires editing multiple times when changes are made outside the class
Problem: Having too many conditional operations or switch makes it harder to understand, and high probability of breaking, testing also becomes difficult 
Suggested Refactoring: Multiple ways
• Introduce polymorphism
• Extract and move – Group things that need to be together, move to introduce polymorphism
• If there is only few cases that affect singe method – use extract method
[Polymorphism can become overkill]

Code Smells – Divergent Change
What could be some issue here with respect to instructor class?
Divergent Change - Refactoring
Extract class and put the functionalities in one place so that one change does not impact others

Code Smells – Divergent Change
One change should not result in changes in ”n” other places within a class
Context: A class has a method to perform an operation which is affected by changes happening in another method in same or different class
Problem: Impacts maintainability and results in a scenario where one needs to know
everything. Also affects the testability and understandability
Suggested Refactoring: Multiple ways
• Extract Class – Put everything that changes together into one class
• Extract method – Check if the operations that change can be wrapped into a single method

Code Smells – Feature Envy
One class depending too much on functions from another class – Envious!!
Feature Envy - Refactoring
Move the method to the class so that the single responsibility principle is also ensured

Code Smells – Feature Envy
Method in a class is envious of features offered by other classes
Context: A class has a method that needs to perform operations for which it depends on multiple data and operations in another class(es)
Problem: Causes coupling between different classes affecting extensibility and changeability. Testing also becomes challenging 
Suggested Refactoring: Multiple ways
• Move method – moving the method to where it belongs
• Extract and move method – When only part of the method has too much dependency

Code Smells – Speculative Generality
Sometimes we over design and overcomplicate things and speculate !!

Speculative Generality - Refactoring
Refactoring by Collapsing the hierarchy
Code Smells – Speculative Generality
Code created with speculation that something will be required in future and never
implemented
Context: Classes have been built after extending classes creating inheritance
relationship but never used or features have been planned but not implemented
Problem: Unwanted complexity affecting understandability. Can lead to some
implementations in the child classes resulting in unwanted behaviour. Can be spotted
when the only use of a class is some test cases.
Suggested Refactoring: Multiple ways
• Collapse Hierarchy – Remove abstract classes not doing much
• Use Inline Class – Remove unnecessary delegation
• Remove unused parameters
• Rename methods to be in line with context
29Five Main Categories Of Smells
•Bloaters – Too many things packed, keeps accumulating
(eg: Long Method, primitive obsession)
•Object Oriented Abusers – Incorrect use of OO principle or even incomplete
(eg: Conditional complexity)
•Change Preventers – Changing one causes change in others
(eg: Divergent Change)
•Dispensables – Code whose absence won’t make a difference, but presence could!
(eg: Speculative Generality)
•Couplers – Creates too much coupling between classes
(eg: Feature Envy)

Quick Reference Cards
Smell NameShort DescriptionSuggested Refactoring
Duplicated CodeSame code in more than one placeExtract Method, pull up field,..
Long MethodToo many things in one methodExtract Method, Decompose
conditionals, Parameter objects,..
Large ClassOne class is doing too muchExtract class, extract sub classes.
Extract interface,..
Long Parameter ListNever ending list of parametersParameter object, Extract Method,..
Divergent ChangeToo many changes in one class for different
reasonsExtract class,..
Shortgun SurgeryOne change => too many changesMove Method, Move field, Inline class,..
Feature EnvyInterested in methods of another classExtract Method, Move Method
Data ClumpsSame data items together in many placesExtract Class, Parameter Object, ..
Primitive ObsessionUsing too many primitive data typesExtract class, Introduce parameter
Object,..
Switch StatementsComplex switch statements, conditionals,..Replace with explicit method, Replace
conditional with polymorphism,..
Parallel Inheritance hierarchiesRequiring parallel subclasses creationMove Method and Move field,..
32Smell NameShort DescriptionRefactoring
Lazy ClassNot doing much, exists there!Collapse hierarchy,..
Speculative GeneralityNo use in the current context than testInline class, Collapse hierarchy,..
Temporary FieldHaving instance variables not used muchExtract class for unused variables,..
Message ChainsAsk to one object which leads to next…Hide Delegate, extract method and
move method,..
Middle ManLots of delegations happeningRemove Middle Man, Inline Method,
Replace delegate with inheritance,..
Inappropriate HierarchyToo much private information shared
between classesExtract class, hide delegate, Extract
class, ..
Alternative Classes different
InterfaceTwo classes having similar methods
Using different interfaceMove method, extract superclass,..
Incomplete Library ClassModifying library class can be impossibleIntroduce local extension, foreign
method,..
Data ClassClasses with just some data fieldsMove method, Extract method,..
Refused BequestSubclasses don’t need everythingPush down method, Push down field,..
CommentsToo much of comments is also badRename method, Extract method,..
Refactoring - Best Practices
• Understand code well before refactoring
• What you think might be a problem may not be a problem!
• Create tests and ensure the tests work just like before or even better
• Keep refactoring small and commit often – Take small steps, test and repeat
• The scope of refactoring needs to be defined clearly
• Sometimes it can end in a loop
• To do notes can be always useful
• It always helps when there are more eyes [Also for project!!]
34Refactoring – Some Tools
• There is no single best tool that are available – Use your intuition along with
existing tools
• IDEs provide a lot of support – Refactor menu (In IntelliJ IDEA)
• SonarLint – Like spellchecker for code, detects smells, checks for any possible
issues (available for IDE’s like IntelliJ, Eclipse, etc.)
• SonarQube – It’s a server, given a repo it finds the list of code smells

LECTURE-VIII
Code Metrics

an some metrics be used to
aid refactoring?
2Code Complexity
The ratio of time spent reading versus writing is well over 10 to 1
--Robert C Martin
• Code over time has tendency to accumulate complexity
• Greater or larger functionality should not have direct impact on code complexity
• Unnecessary complexity affects maintainability, time to market, understandability and testability
How to manage it? – Start measuring it!!

What is measurement?
Measurement is defined as the process by which numbers or symbols are assigned to attributes of entities in the real world in such a way as to describe them according to clearly defined rules

What is measurement?
• Entity: can be an Object (person) or event (journey )
• Attribute: Feature of property of entity (height, blood pressure, etc.)
• Two types of measurement:
• Direct measurement: measurement of attribute
• Indirect measurement: Measurement of attribute involves measurement of some other attribute (eg: BMI)
• Uses of measurement – Assessment or Prediction

Measurement In terms of Software
• Carried out throughout the software development process
• Measurements can be performed at different levels
• Completed Product ( reliability, performance, etc.)
• Development Process (time, man hours, etc.)
• Source Code (lines of code, cyclomatic complexity, etc.)
• Source code metrics focus on measuring the source code of a system
• Allows to measure complexity of code
• Improve quality of code and thereby overall software
• Used for lot of applications (defect prediction, fault localizations, refactoring, testing, etc.)

Commonly Used Source Code Metrics
• Lines of Code (LOC)
• Easiest but effective indicator of complexity
• Small modules have low defect rates as opposed to large ones
• Cyclomatic Complexity
• Developed by Thomas McCabe, 1976
• Allows to measure the complexity with respect to control flow of the code
• Halstead Software Science Metrics
• Developed by Halstead, 1977
• Measures complexity in terms of the amount of information in source code
• There are also object oriented metrics (Chidamber and Kemerer 1994,
Li and Henry 1993)

Cyclomatic Complexity
• Count of the number of linearly independent paths in a program
• Has a big impact on testing – test cases needs to cover the different paths
• Uses the control flow graph, G of the given program – Approach based on graph theory
• V(G) = e – n + 2p
• e = Number of edges
• n = Number of nodes
• p = Connected components
In practice the number boils down to 1 (base) + number of decision points 8 Cyclomatic Complexity - Simple Example
Complexity = 4 – 5 + 2*1
=1
Cyclomatic Complexity - Another Example
Complexity = 8 – 8 + 2*1
=2

Halstead Software Science Metrics
• Considers program as a collection of tokens
• Tokens: Operators or operands
• The metrics makes use of the occurrence of operators and operands in a program to
reason about complexity
n1 -> number of distinct operators (+, -, *, while, for, (), {}, function calls, etc.)
n2 -> number of distinct operands (variables, method names, etc.)
N1 -> total number of occurrence of operators
N2 -> total number of occurrence of operands
• The above observations are combined to provide different metrics

Halstead Software Science Metrics
• Vocabulary, n = n1 + n2
• Program length N = N1 + N2
• Volume, V = Nlog2 (n)
….
Operators (+, *, =, double, int,
final, return, {, }, (, ) ), n1 = 11
Operands (calculateTotalCost, item1, item2, sum, tax, number1, number 2, totalCost) = 8
N1 - (1, 1, 3, 3, 3, 1, 1,1,1,1,1) = 17
N2 – (1, 1, 1, 2, 2, 1, 1, 2) = 11

Six OO Metrics – Chidamber and Kemerer
• Weighted Methods per Class
• Depth of Inheritance Tree
• Number of Children of a Class
• Coupling Between Object Classes
• Response for a Class
• Lack of Cohesion on Methods

LECTURE IX
Design Patterns: An Introduction
What were some Lessons
Learned form Unit 1?
Key Design Principles
• Abstraction
• Encapsulation
• Modularization
• Hierarchy
Designing that too OO Systems is not very straightforward

Things Improve with Practice
• Designs should be reusable, flexible and understandable
• Very difficult to get it right the first time – Not hard though!!
• Experience people also take multiple iterations
• Novice find it even more difficult to get their head around!

Things Improve with Practice
• Experts tend to reuse solution that have worked in the past!
• The way objects are identified, relationships are established becomes recurring
activity
• When something has been tried and worked well, why not use it again!!
• They start seeing recurring patterns over time
• What if this experience could be recorded for reuse?

GRASP
General Responsibility Assignment Software Patterns or Principles
• Information Expert: Who gets the responsibility?
• Find which class has the data
• The one who has data also should have the operations to perform the data
• Creator: Who gets the role of the creator?
• Defines guidelines for which class should be in charge of creating objects of
other type
• E.g. Class B should be in charge of creating objects of A if:
• B contains or compositely aggregates A
• B closely uses A
• B has inputs to construct A
• B records A

General Responsibility Assignment Software Patterns or Principles
• Low Coupling: How to minimize impact of change?
• Assign responsibilities such that to reduce coupling
• Given two alternatives, chose the one that minimizes coupling
• High Cohesion: How to keep everything together in one object to better manage
and to minimize coupling?
• Do one thing and do it very well
• Give one end-to-end responsibility to one class
• Reduce communication
11General Responsibility Assignment Software Patterns or Principles
• Polymorphism: How to decouple clients from different ways of accomplishing a
single task?
• Contributes to low coupling
• Several ways to accomplish a task or a functionality
• Achieved through interfaces, overloading methods of super classes
• Pure Fabrication: Whom to assign the responsibility when it does not fit into
either of the classes?
• Promotes cohesion
• Sometimes a responsibility needs to be assigned but need not fit well into a class
• Create a new class (does not map to domain object for handling the responsibility
12General Responsibility Assignment Software Patterns or Principles
• Indirection: How to ensure that one can communicate with another without
knowing each other well?
• Another principle/pattern to reduce coupling
• Introduce a new class between two classes A and B
• Changes in A or B doesn’t affect each other. The intermediary absorbs the
impact
• Introduces a class as opposed to protected variation
• Protected Variation: How to protect part of a class from changes in part of
another class?
• Related to ensuring low coupling
• Code of a part of class B is protected from changes in code of part A
• Introduce interface around the unstable part of the codebase
13General Responsibility Assignment Software Patterns or Principles
• Controller: What if there is a need for someone to control the responsibility
between classes?
Kind of a subtype of pure fabrication
Very common in UI applications -> between UI and the backend
Separate concerns clearly between two classes by having someone in middle
Does not map to any domain object

Design Patterns
Design Patterns
Each Pattern describes a problem which occurs over and over again in our
environment and then describes the core of the solution to that problem, in such a
way that you can use this solution a million times over, without ever doing it the
same way twice
-- Christopher Alexander
Patterns captures {Context, Problem, Solution}
What are some of the patterns you can think of?
Source: A Pattern Language, Christopher Alexander
16Patterns patterns everywhere!
• We have a natural tendency to look for patterns in anything and everything
• Pattern of grades for courses
• Pattern of questions in question papers
• Climate patterns (rainfall, summer, …)

What about Software?
Many patterns to design and build software systems
• Architectural Patterns [Higher Level]
• Design Patterns [Lower level]

Four Elements of a Pattern
• Pattern Name: Handle to describe a design problem
• Problem: When to apply the pattern, preconditions, special relationships, etc.
• Solution: Elements that make up the design, relationships and collaborations
• Not a particular solution but abstract representation with potentials
• Consequences: Results and trade-off of applying a given pattern
• Perform cost-benefit analysis

Design Patterns
• Principles, relationships and techniques for creating reusable OO design
• Identifies participating objects, their roles, responsibilities and relationships
• Not about Linked Lists, hash tables, etc.
• The are low level structures inside classes
• Not about complex domain specific design or design of subsystems
• Domain specific design is more at high level – Architectural level

Classification of Design Patterns
• Mainly divided into three based on the purpose they serve
• Creational, Structural and Behavioral
• Each category has a purpose, a set of patterns that work in different scope:
• Class or object
• There are a total of 23 classic patterns: Gang of Four (GOF) patterns
• The famous book Design Patterns: Elements of Reusable Object-Oriented 
. Software by Erich Gamma, Richard Helm, Ralph Johnson and John Vlissides

Classification of Design Patterns
• Creational
• Class - Defer creation to subclasses
• Object – Defer creation to another object
• Structural
• Class – Structure via inheritance
• Object – Structure via Composition
• Behavioral
• Class – algorithms/control via inheritance
• Object – algorithms/control via object groups

C – Scope is Class
O – Object Scope
23Describing Patterns
• Pattern Name and Classification
• Name captures essence and classification the category it tackles
• Intent
• What does the design pattern do?
• What is its rationale and intent – What problem does it address?
• AKA (Also Known As): Other known names
• Motivation
• A scenario that illustrates the problem and how pattern can solve it
• Applicability
• What are the situation in which the pattern can be applied and how to
recognize them?
24Describing Patterns
• Structure
• Graphical representation of the pattern in UML or other modeling language
• Participants
• The classes/objects participating and their responsibilities
• Collaborations
• How the participants collaborate to carry out their responsibilities.
• Consequences
• How well does the pattern support its objectives?
• What are the trade-offs and results of using the pattern?
• What part can be varied independently?
25Describing Patterns
• Implementations and Sample Code
• Code fragments to illustrate implementation in OOP language of choice
• Known Uses
• Examples of patterns in real systems
• Related Patterns
• What are the patterns closely related to this one?
• What are the key differences?
• What other patterns with which this can be used?

Some Principles
Program to Interface Not Implementation
• One of the most important OO Design Principles
• “Program to interface” refers to the idea of ensuring loose coupling
• Does not only mean the “Interface”?
• Very useful when lot of changes are expected
• Create an interface, define methods -> create classes that implements them
• Allows external objects to easily communicate
• Maintainability and flexibility increases
28Favor Object Composition over Class Inheritance
• Two most common techniques: Inheritance and Composition
• Class inheritance: White-box reuse
• Internals of parent class are visible to child class
• Defined statically at compile time
• Sub class can override methods of parent class
• Inheritance is not always the go to solution - ”breaks encapsulation”
• Composition: Black-box reuse
• Objects acquiring references to other objects
• Defined dynamically at run time
• Encapsulation is not broken – Objects are accessed through interfaces
• Get what is needed by assembling and not by creating

LECTURE-10
Design Patterns
Meet the Observer Pattern!
• Subscriber chooses the (channel) publisher by pressing on subscribe button
• The channel who is posting (Publisher) delivers only to its subscribers
• publisher has to maintain a list of subscribers (channel subscribers)
Meet the Observer Pattern: Motivation
Can we push the data to all clients as soon a s it arrives?
Meet the Observer Pattern
• What if we had the sensor data to be publishers?
• What if the clients just become subscribers?
• Every time data comes, all the subscribers are notified
• Publishers and subscribers can be decoupled
• Adding new clients also is just same as adding a new subscriber
6Observer Pattern: Documentation
Intent
Defining a one-to-many dependency between objects
Change in object notifies all dependent objects
Also Known As: Dependents, Publish-subscribe
Motivation
• Maintaining consistency between objects
• Reduce tight coupling and increase reusability
• Two key objects: Subject and Observer
Example: Presentation components and application data
Observer Pattern: Documentation
Applicability
• When abstraction has two aspects – One dependent on the other and separation
promotes reusability
• Eg: Think of having just one class, Display instead of mobile and web
• When a change in one object requires changing others [Not clear how many!]
• When object should notify others without assuming about the objects [reduce coupling]
Observer Pattern: Documentation
Observer Pattern: Documentation
Participants
Subject (IoTInterface)
• Knows its observers – Many observers per subject
• Provides interface for attaching and detaching observer objects
Observer (DataSubscribers)
• Defines an update interface for objects that should be notified
Concrete Subject (RfidPublisher)
• The key subject that contains the state information
• Sends a notification to its observers when state change happens
Concrete Observer (MobileSubscriber)
• Maintains reference to concrete subject object
• Implements observer update interface
This Photo by Unknown Author is licensed under CC BY
10Observer Pattern: Documentation
Consequences
• Abstract coupling between Subject and Observer
• Subject doesn’t know the concrete class of any observer
• The coupling is as minimal as possible
• Support for broadcast communication
• Subject doesn’t care about number of observers
• The notifications are automatically sent as broadcast to all interested 
• Unexpected updates
• Unintended updates on subject may cause cascade of updates on observers
• Often simple update notification may not provide enough changes on state changes of subject
11Observer Pattern: Documentation
Implementation
Check the source code given along: IoTObserver

LECTURE-XI
Design Patterns
Let’s build a factory to create objects – Factory Pattern!
[Creational]
Meet the Factory Pattern!

Meet the Factory Pattern: Motivation
Enroll function may be different in each! We may want to add more in future - Elective

Meet the Factory Pattern
• What if we want to easily add new products (objects of new type)?
• What if you don’t want to change too many places when something is added?
• Decoupling clients from knowing actual products (program for interface)
• Encapsulate object creation (encapsulate what varies)

Factory Pattern: Documentation
Intent
Defining an interface for creating object but let subclasses decide which class to be instantiated
Also Known As: Virtual Constructor Motivation
• Not clear which of the subclasses of the parent class to access
• Encapsulate the functionality required to select a class to method
• Two key objects: Factory (Creator) and Product

Factory Pattern: Documentation
Applicability
• A class can’t anticipate the class of objects it must create
• Class wants subclasses to specify the object it creates
• Classes delegate responsibility to one of the several helper classes and which is the delegate needs to be localized
Factory Pattern: Documentation
Factory Pattern: Documentation
Participants
Product (Systems Interface)
• Defines the interface of objects the factory method creates
Concrete Product (RegularSystemsCourse)
• Implements the product interface
Creator (CourseFactory)
• Declares the factory method which returns object of type product
• Calls factory method to create the product
Concrete Creator (RegularCourseFactory)
• Overrides the factory method to return instance of concrete product

Factory Pattern: Documentation
Consequences
• Eliminates the need to bind application-specific classes into code
• Code only deals with the product interface
• Any number of concrete products can be added
• Provides hooks for subclasses
• Creating objects inside a class is more flexible than direct creation
• Connects parallel hierarchies
• Class can delegate some of its responsibilities to another class
• Those can also use the abstract factory
• Too much of subclassing can happen
• Code can become too complicated
• Becomes more easier to introduce factory to existing hierarchy

Factory Pattern: Documentation
Implementation
Check the source code given along: CourseFactory
We can always use an adapter: Adapter Pattern!
[Structural]

Meet the Adapter Pattern!
Meet the Adapter Pattern – A Scenario
Why don’t we write an adapter that can transform?
Meet the Adapter Pattern
• What if the interfaces are incompatible?
• What if we can have an adapter in between that can transform the new format?
• Adapter wraps the complexity of conversion
• Supports collaboration of different types of object
• Two-way adapter can also be made
Adapter Pattern: Documentation
Intent
Convert the interface of a class into another interface expected by the clients
Also Known As: Wrapper
Motivation
• Not every time there are compatible interfaces
• Promote reusability
• Three key objects: Client, Target, Adapter
Example: Adapter to transform data [Think of legacy class that accepts only certain formats]

Adapter Pattern: Documentation
Applicability
• There is an existing class but its interface does not match the one needed
• Creation of reusable class that can work with unforeseen classes
• There are several existing subclasses but impractical to adapt their interface by subclassing everyone
• Use object adapter [The one we use here] – Uses composition
• Class adapter relies on multiple inheritance
Adapter Pattern: Documentation
Adapter Pattern: Documentation
Participants
Target (NodeData)
• Defines the domain specific interfaces that the client uses
Client (NodeManager)
• Collaborates with objects conforming to their target interfaces
Adaptee (VideoNode)
• Defines an existing interface that needs adapting
Adapter (VideoNodeAdapter)
• Adapts the interface of the Adaptee to the Target interface
Adapter Pattern: Documentation
Consequences
• Single adapter can be used for many adapteees
• Can implement different functionalities to work with many adaptees
• New types of adapter can also be easily introduced
• Provides good separation of concerns
• Keep the logic for conversion in one
• No need to change at multiple places
• Overall complexity may increase – How much of adaptation is done?
• Can it be done in a simpler manner on the Adaptee or Target?
Adapter Pattern: Documentation
Implementation
Check the source code given along: IoTAdapter

LECTURE - XII
Design Patterns
Strategies can be different:
Strategy Pattern! [Behavioral]
Meet the Strategy Pattern
• What if you want to alter objects behavior at run-time?
• What if there are similar objects but the way they work is different?
• Each variety of algorithm may require its own set of data and functions

Strategy Pattern: Documentation
Intent
Define a family of algorithms, encapsulate each one and ensure they are interchangeable. Strategy lets algorithm change depending on the client, who is using it
Also Known As: Policy
Motivation
• Different algorithms will be appropriate at different times
• Promotes maintainability
• Two key objects: Context and Strategy
Example: Think of Google maps -> selection of mode of transport

Strategy Pattern: Documentation
Applicability
• Many related classes differ only in their behavior
• There is a need for different variants of an algorithm
• Algorithm might require data that client needs not know about – avoid exposing algorithm specific data structures
• Class defines many behaviors and these appear as multiple conditional statements

Strategy Pattern: Documentation
Strategy Pattern: Documentation
Participants
Strategy(PaymentType)
• Interface common to all algorithms. Used by context
ConcreteStrategy (DebitCard)
• Implements algorithm using strategy interface
Context (Booking)
• Configured with ConcreteStrategy object
• Maintains reference to a Strategy object
• Can define interface for Strategy to access data

Strategy Pattern: Documentation
Consequences
• Families of related algorithms
• Hierarchies of strategy classes define a family of algorithms or behaviors
• Inheritance can help in factoring out common functionality
• Alternative to subclassing
• Inheritance is another mechanism – Hard-wires context [coupling!]
• Eliminates conditional statements
• Encapsulates behavior separately [Good solution for long method smell]
• If the number of variations are less - Don’t overcomplicate!
• Classes must be aware of different possible strategies
10Strategy Pattern: Documentation
Implementation
Check the source code given along: EBikePaymentStrategy

How about building things:
Builder Pattern! [Creational]
Meet the Builder Pattern!
• What if there is a complex object?
• Can we avoid instantiation of a huge constructor?
• Not every time all constructor parameters are required
• Allows extraction of object construction code to separate object
• Creation of an object is just about assembling other objects step by step
• A very decoupled approach to creation

Builder Pattern: Documentation
Intent
Separate construction of complex object from representation such that same
construction process can result in different representations
Also Known As: Builder
Motivation
• Separate object construction from business logic
• Promote readability and understandability
• Three key objects: Director, Builder, Product
Example: Builder to build different types of vehicles [Each has engine, tyre, etc]
Applicability
• Algorithm for creating the object must be independent
• Different parts may make up the object
• Need not worry about how they are put together
• Construction of different representations of the object needs to be supported

Builder Pattern: Documentation
Participants
Builder (StudentBuilder)
• Defines the interface for creating parts of a product object
ConcreteBuilder (ConcreteStudentBuilder)
• Assembles the parts to create product by implementing builder interface
Director (StudentDirector)
• Constructs an object using the builder interface
Product (Student)
• Complex object under construction
• Includes classes that define the different parts

Consequences
• Easily vary products internal representation
• Director gets the abstract interface to build a product
• All that needs to be done is to define a new kind of builder
• Isolate code for representation and constructions
• Concrete builder contains code for building a kind of product
• Directors can reuse builders to build different variants of product
• More control over the construction process
• Step by step approach under directors control – Focus is on the process
• The overall code complexity increases due to multiple classes
• Benefits in the long run
20Builder Pattern: Documentation
Implementation
Check the source code given along: StudentRecordBuilde

