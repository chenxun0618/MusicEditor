package cs3500.music.view;

import cs3500.music.model.Note;

/**
 * to represent a music editor view interface
 */
public interface IMusicEditorView {

  /**
   * starts/resumes playing the song
   */
  void start();

  /**
   * pauses the song
   */
  void pause();

  /**
   * restarts the song
   */
  void reStart();

  /**
   * @return the current beat of the song
   */
  int getCurrentBeat();

  /**
   * adds a note to the song
   * @param n a note
   */
  void addNote(Note n);

  /**
   * removes the note from the song
   * @param n a note
   */
  void removeNote(Note n);

}
