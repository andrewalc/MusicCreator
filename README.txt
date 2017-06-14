--------------------------
Java MusicEditorModel
--------------------------
Written by Andrew Alcala: alcala.a@husky.neu.edu

---------------
Class Structure
---------------
Tones.java: Enumeration

At the entire packages core is the Tones enumeration representing notes C through B. 
I used an enumeration as these notes are constant throughout music and using them to identify notes through a simple enumeration seemed like a logical way to implement notes. 
The enumeration allows for methods that assist with higher level objects that use the enum, especially the Pitch class. 
Since the enum is final, methods get next tone and getprevtone are easy to cycle through as they infinitely cycle in music and can do the same in the editor.

Pitch.java: Class

Pitch's purpose is to serve as the union of a Tone and an octave. Music calls for notes to have octaves, different levels of the same tone, and exist together. 
The pitch class allows for the union of both and provides methods that relay information about the sound itself, returning what octave the pitch is what tone it is 
and also being able to tell what the next Pitch or previous Pitch and return it. The pitches class is also great for the sorting of all notes in the Piece class, as the TreeMap 
runs on a Map<Pitch, ArrayList<Note>>. By being able to identifying what pitches there are in a piece, sorting sounds in a music piece becomes alot easier. Pitch is more specific 
than a Tone, but more specific than a Note, allowing it to be useful in various situations.

PitchComparator.java: Class

PitchComparator was made to give the Piece class's TreeMap<Pitch, ArrayList<Note>> a way to sort its key values and organize them from lowest musical 
pitch to highest musical pitch. This helped greatly in making the getLowestPitch and getHighestPitch methods in Piece since the firstKey would correspond to 
the lowest and the lastKey would correspond to the highest. It made logical sense to sort this way, so this comparator is overall a great for peace of mind.

Note.java: Class

Note is the ultimate object representation of sound of music. Its constructor requires a Tone, ocatave int, starting beat int(representing what beat this note will begin playing at), 
and a beats int (representing how many beats the note will sustain for). This class was made spefically with the idea of a music piece, and ultimately the editor in mind. It 
requires the startingbeat and beats parameters solely to identify its place on a music sheet, when it will exactly play on what beat and for how long. This class takes timing 
into account, while Pitch does not. The class' method consist of mostly getters and setters. Equals and hashcode were overriden accordingly (as in Pitch and Piece) as Note will 
be compared a lot in the Piece and Editor class. The class also contains the final static strings for note head, note sustain, and note empty, as these specific strings are seen 
in other classes as well. 

Piece.java: Class

Piece is the representation of a simple piece of music. Its purpose is to contain all of its notes, and be able to relay information about key properties of the piece,
 including key notes and beats. A piece holds all of its notes in a TreeMap<Pitch, ArrayList<Note>. I designed the piece this way because to me it made sense to sort all 
 notes by their pitch. By using Pitch as a key, putting new notes into the piece would be as simple as getting the notes tone and octave. Using the pitchCompartor it would 
 even be sorted on its own. I used ArrayList<Note> as the value for the map so I can contain multiple notes under the key of a unique pitch. Should I need to access notes of 
 a given pitch, I simply need to get the list. Since all notes contain their own timing information, I do not need another sublevel to hold beats in specfic spots. A simple 
 check if the current beat is within the range of a notes beat range is enough to know when a Note should play. 
Piece contains the functionality to add remove and modify it's notes and is the connection between the editor and note management. With every modification to note structure 
in a piece, the piece will self-update its values, specifically maxBeats and the TreeMap itself. Should a note modification extend or shrink the length of a piece through 
adding removing or modifying, updateMaxBeats will check itself to find out the new maximum beat. Should an ArrayList<Note> corresponding to a pitch no longer have any notes 
in it due to removing a note, that key mapping will remove itself. Piece can find out what is the highest or lowest note present out of all notes and find out how many notes 
there are in total. The class checks itself all through the TreeMap, and does so easily. Finally, the toString method relays all the information about a piece of music visually,
 showing the range of pitches from lowest to highest, the range of beats from 0 to the last note, and most importantly shows where Notes start, sustain, and end. 


MusicEditorModel.java: Interface and implemented in a Class

The music editor model is the main hub of the editing mechnaics. The purpose of this class is to give users
an easy access to the editing tools necessary to create music through Notes. Creating a new instance of a MusicEditor allows for immediate editing of a blank Piece, a piece of music.
 Users can begin a new piece, load a piece, or export a piece using those respective methods. Adding, removing, and modifying notes are all avaliable and only require the user to enter
  Note objects representing what they want to add or remove, and provide new parametes should they wish to modify an existing note. The editor also allows for merging pieces of music, 
  either merging directly on top or and the end of another piece. Finally a toString method outputs a visually of the piece in its current state.

------------
What to do
------------
To begin editing music, create a main class and create a new MusicEditorModel() which will begin you with a blank piece.

MusicEditorModel model = new MusicEditorModel();

----------------
How to add a note:
-----------------
First create a new Note.

Note note1 = Note(Tones tone, int octave, int startingBeat, int beats);

Tones is an enum, here is the full list of possible tones. 

  Tones.C, Tones.C_SHARP, Tones.D, Tones.D_SHARP, Tones.E, Tones.F, Tones.F_SHARP, Tones.G, Tones.G_SHARP, Tones.A, Tones.A_SHARP, Tones.B

Once your note is made, enter it into the addNote method in your model.

model.addNote(note1);

The note has been added!

Check out the visualization by printing model.toString()

----------------
Removing and Modifying
----------------
The removeNote method require you to give a note the is exactly the same as the one you want to remove in the piece.
The modifyNote method requires an exact note of the note you with to modify, and then new note parameters to change your requested note to.

-----------------
Exporting and Loading Music
-----------------
You can export your piece at any time by calling exportPiece()
In the same way you can import pieces by calling loadPiece and giving it a Piece to load.

-----------------
Combining Pieces
-----------------
The editor allows you to combine two pieces together using combinePieceOnTop and combinePieceAtEnd to combine the piece currently in the editor with an external piece that you give it.
combinePieceOntop will directly overlay the loaded piece with the given piece, combinePieceAtEnd will combine the two from the loaded pieces end to the external pieces' beginning.