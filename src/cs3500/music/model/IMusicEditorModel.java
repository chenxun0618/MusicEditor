package cs3500.music.model;

import java.util.List;

/**
 * to represent a music editor model that can edit(add, remove and retrieve notes,
 * mix songs, play songs, render music animation) a piece of music
 */
public interface IMusicEditorModel {

  /**
   * adds the given notes to a piece of music
   * @param notes a list of notes to be added
   */
  void addNotes(List<Note> notes);

  /**
   * removes the given notes from a piece of music
   * @param notes a list of notes to be removed
   */
  void removeNotes(List<Note> notes);

  /**
   * mixes that music with this music in a specific type
   * @param that the music to be mixed
   * @param type either simultaneously or consecutively
   * @return a new piece of music with the given music mixed to this music in a specific way
   */
  Music mix(Music that, Music.MixType type);

  /**
   * retrieves the notes at a specific beat
   * @param beat a beat in the music
   * @return a list of note(s) retrieved at this specific beat
   */
  List<Note> retrieveNotes(int beat);

  //returns all pitches starting from the lowest pitch to the highest pitch in this music
  List<Pitch> getPitches();

  //sets the tempo of the music
  void setTempo(int tempo);

  //returns the tempo of the music
  int getTempo();

  //returns the duration of this music
  int getDuration();

  //prints the music as a string
  String printMusic();
}
