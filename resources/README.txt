(Interface) IMusicEditorModel:

An interface representing a Music editor model. In this model, music notes suitable for MIDI are represented as an ArrayList of Integer of size 5. The ArrayList of Integer is formatted as
follows, (int startingBeat, int endBeat, int instrument, int pitch, int volume). Starting beat is the int where a beat begins, endBeat is an int beat that represents what beat a note stops
playing at. int instrument represents the MIDI integer instrument this note will play as. int pitch is the official MIDI int pitch value this note will play as. Finally int volume represents the MIDI volume value a note will play as. Notes should be stored into a Map of Integer to ArrayList ArrayList of Integer, where the Integer keys are MIDI note pitches, and their values are ArrayLists that contain all notes that share that buckets key pitch. To make adding, removing, and modifying notes easier, the Tones enumeration should be used in
those respective methods to describe the tone of a note, ranging from C to B.

(Class) MusicEditorModel:

Implements the IMusicEditorModel, and all of its methods. This is the public model the user will see and take their actions editing a piece through. Outside of all these actions the model can take from its implemented Interface, MusicEditorModel has a builder, which creates a new instance of the model itself. This is very important later on for the creation and running of our main class, which takes in String commands and initializes the proper view that was

(Class) Note:

A note is the most basic unit in a piece of music, and is constructed with a Pitch, starting beat, duration, instrument type, and volume. Pitch is the essential identifier for a Note, as it describes a Note's location on the musical scale. A Note's starting beat identifies where in the Piece (timewise) the Note takes place. The duration is (unsurprisingly) how long the note is held (measured in beats), and the instrument and volume of a note indicate the actual "sound" of the note when it is played through a midi player. Through the Model's methods, a Note can be added, removed, or modified in any of its fields. Notes can also overlap and create harmonies (which is especially useful with a song with multiple instruments being played at once at the same Note and Pitch).

(Class) Piece:

Piece is the representation of a simple piece of music. Its purpose is to contain all of its notes, and be able to relay information about key properties of the piece,
 including key notes and beats. A piece holds all of its notes in a TreeMap<Pitch, ArrayList<Note>. Using the pitchCompartor it would even be sorted on its own. We used ArrayList<Note> as the value for the map so that we can contain multiple notes under the key of a unique pitch. Should we need to access notes of a given pitch, we simply need to get the list. Since all notes contain their own timing information, we do not need another sublevel to hold beats in specfic spots. A simple check if the current beat is within the range of a notes beat range is enough to know when a Note should play.
Piece contains the functionality to add remove and modify it's notes and is the connection between the editor and note management. With every modification to note structure
in a piece, the piece will self-update its values, specifically maxBeats and the TreeMap itself. Should a note modification extend or shrink the length of a piece through
adding removing or modifying, updateMaxBeats will check itself to find out the new maximum beat. Should an ArrayList<Note> corresponding to a pitch no longer have any notes
in it due to removing a note, that key mapping will remove itself. Piece can find out what is the highest or lowest note present out of all notes and find out how many notes
there are in total. The class checks itself all through the TreeMap, and does so easily. Finally, the toString method relays all the information about a piece of music visually,
 showing the range of pitches from lowest to highest, the range of beats from 0 to the last note, and most importantly shows where Notes start, sustain, and end.


(Class) Pitch:

When a Note is constructed, it is given a Tone and an Octave. These two fields are sent to the constructor for a Pitch, which is an object that holds this information about a Note. The tone gives the Note its musical identity, and represents what sound on the scale is played (e.g. C, C#, D, D#), and the octave  indicates in what scale the note is being played. A Pitch...

(Class) PitchComparator:

PitchComparator is a class that contains a comparison method, used for sorting a Piece's TreeMap. It sorts all of the items in the TreeMap by their Pitches, from lowest to highest. This class is especially useful when we are trying to find a Piece's highest and lowest used Pitch.

(Enum) Tones:

Tones is an enumeration that holds all of the possible letter "notes" on a musical scales (ranges from C to B, including all sharp (#) notes). Using an enumeration for this type of data is extremely useful, especially in scenarios when we need to check if a Note is valid. Rather than having to check for individual Strings and parsing through them with long switch statements, we can simply check through our enumeration of Tones to see if there is a match. A tone has a String name and an integer value, both of which are used for identifying and indexing a Tone.

(Interface) Composition Builder:

This interface represents a parameterized, generic builder. It contains three methods: build, addTempo, and addNote. "addTempo" sets the tempo of a generic piece of music to the given amount, "addNote" adds a note to the generic piece of music, and finally, "build" puts together and returns the piece of music as the type that it is set as.

(Class) Music Reader:

This class contains the "parseFile" method, which reads through a text file of music, 'parses' it, and converts it into our implementation of a Piece.

(Class) Console View:

A class that represents a view of a Piece within the console of whatever program is running it.

(Class) EditorPanel:

This class is responsible for drawing the scales on which a given song's notes can be placed, drawn, and represented. It also draws a movable red line to indicate the current beat that is being displayed on the Piano portion of the visualization. The Panel does scale to size depending on the number of octaves being used in the song, as well as the length of the song (in beats).

(Interface) IMusicEditorView:

This interface holds the method initialize, which is the key method for getting the view of a MusicEditor of a certain type. Any visualization type the implements this Interface (and thus this method) can be called on to represent a MusicModel.

(Class) IntegerComparator:

A simple Integer Comparator to organize a list of integers in ascending order. (Used for a TreeMap)

(Class) KeyboardPanel:

This class represents the panel on which the piano visualization is drawn. Draws keys on a piano one at a time, and checks the state of the current beat before drawing. For each note active on this "current beat", the corresponding key on the keyboard is lit up as orange. As the user moves the "red line" in the editor panel visualization, the current beat is also changed, and thus the piano representation is redrawn to show the new current keys being hit at the time.

(Class) MidiView:

This class implements the IMusicEditorView interface, and represents one of the three possible views available in this iteration of the Music Player. Running this view activates a Midi player, which then plays the given song (in sound). While there is no "visual" aspect to this view, the view does take into consideration: the instruments being used at each beat, the tempo of the song, harmonies between multiple notes at the same time, and volume of each note being hit.

([Main] Class) MusicEditor:

The main class of the program. Runs a given "piece" in text form with the given visualization command (also in String). Calling this method runs a factory, which parses the given view types, and calls the builder for that view so that it may be enacted.

(Class) MusicEditorView:

This is the class that is responsible for drawing the window that the visual representation of a MusicModel is placed on. This class has the method "getNotesAtBeat" and "getKeyPress", which (respectively) gets all of the active notes at a given beat, and takes in and processes key commands.

(Class) NoteTxt:

A class whose main function is to assist in translating from txt music to the Model's implementation of a Note.

(Class) ViewFactory:

The factory that is activated by the calling of the main class/method. Depending on the given String command, this class' method "getView" will return a builder of the requested view. For the command "console", it will build a console representation; for the command "visual", it will build the JPanel pop-up visualization; for the command "midi", it will build a Midi player and play the song.

(Class) CompositeView

A class that combines both a Visual View and Midi view into one, allowing for the music editor to fully sync its audio and visual components. This class is the ultimate product of our Music Editor and allows the user to control the editor using keyboard keys and mouse clicks. If a user presses the right or left arrow keys, they will advance the music editor playhead forward or backward one beat. If the user presses the home or end keys, the editor will move the playhead to the beginning or the end of the music editor. If a user presses the spacebar, the music will begin playing through MIDI, and if the music is already playing then another spacebar press will pause the music at the current playhead beat. If a user clicks on a piano key at the bottom of the screen, a note of beat duration one will be placed in the editor at the play head beat. The note that is added with have the pitch of the piano key that was clicked on by the mouse.

(Class) KeyboardListener

A class that creates a keylistener to be used in a view. This key listener is create to accept a Map of Integer(Keycodes) to Runnable, commands. The class has a method to set it's mapping and then has a method to call the correct command whenever a key on the keyboard is pressed. If the key is found in the map then the runnable command associated with the key will be executed.

(Class) KeyboardListener

A class that creates a keylistener to be used in a view. This key listener is create to accept a Map of Integer(MouseEvent IDs) to Runnable, commands. The class has a method to set it's mapping and then has a method to call the correct command whenever a mouse button is pressed. If the key is found in the map then the runnable command associated with the key will be executed.

(Class) MusicEditorController

A class that acts as the controller for running the MusicEditor. In this class, the maps for the keylistener and mouselistener are created and set. The controller takes in a IMusicEditorModel and a IMusicEditorView and helps bring them together to act as one. The listeners set are added to the given view and enabled through them. Once it is set, the controller can begin the process of running the program by calling beginControl(). One control has begun, it constantly updates the view to respond to anychanges that appear in the model or within itself. This is the loop that allows the editor to being functioning.
