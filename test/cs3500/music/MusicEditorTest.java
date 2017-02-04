package cs3500.music;

import org.junit.Test;


import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Music;
import cs3500.music.model.Note;
import cs3500.music.model.PitchName;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiMock;

import static org.junit.Assert.assertEquals;

/**
 * the test class for music editor
 */
public class MusicEditorTest {
  private Pitch p1 = new Pitch();

  @Test(expected = IllegalArgumentException.class)
  public void noteConstructorTest() {
    Note note = new Note(p1, -1, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noteConstructorTest2() {
    Note note = new Note(p1, 4, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pitchConstructorTest() {
    Pitch pitch = new Pitch(PitchName.C, false, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void musicConstructorTest() {
    List<Note> notes = new ArrayList<>();
    Music music = new Music(notes);
  }

  @Test
  public void printPitchTest() {
    assertEquals("C4", p1.printPitch());
    assertEquals("G#5", new Pitch(PitchName.G, true, 5).printPitch());
    assertEquals("F4", new Pitch(PitchName.E, true, 4).printPitch());
    assertEquals("C5", new Pitch(PitchName.B, true, 4).printPitch());
  }

  @Test
  public void printMusicTest() {
    List<Note> notes1 = new ArrayList<>();
    Pitch e3 = new Pitch(PitchName.E, false, 3);
    Pitch g3 = new Pitch(PitchName.G, false, 3);
    Pitch c4 = new Pitch(PitchName.C, false, 4);
    Pitch d4 = new Pitch(PitchName.D, false, 4);
    Pitch e4 = new Pitch(PitchName.E, false, 4);
    Pitch g4 = new Pitch(PitchName.G, false, 4);

    notes1.add(new Note(e3, 56, 7));
    notes1.add(new Note(g3, 0, 6));
    notes1.add(new Note(g3, 8, 6));
    notes1.add(new Note(g3, 16, 7));
    notes1.add(new Note(g3, 24, 1));
    notes1.add(new Note(g3, 32, 7));
    notes1.add(new Note(g3, 40, 7));
    notes1.add(new Note(g3, 48, 7));
    notes1.add(new Note(c4, 4, 1));
    notes1.add(new Note(c4, 36, 1));
    notes1.add(new Note(c4, 56, 7));
    notes1.add(new Note(d4, 2, 1));
    notes1.add(new Note(d4, 6, 1));
    notes1.add(new Note(d4, 16, 1));
    notes1.add(new Note(d4, 18, 1));
    notes1.add(new Note(d4, 20, 3));
    notes1.add(new Note(d4, 34, 1));
    notes1.add(new Note(d4, 38, 1));
    notes1.add(new Note(d4, 48, 1));
    notes1.add(new Note(d4, 50, 1));
    notes1.add(new Note(d4, 54, 1));
    notes1.add(new Note(e4, 0, 1));
    notes1.add(new Note(e4, 8, 1));
    notes1.add(new Note(e4, 10, 1));
    notes1.add(new Note(e4, 12, 2));
    notes1.add(new Note(e4, 24, 1));
    notes1.add(new Note(e4, 32, 1));
    notes1.add(new Note(e4, 40, 1));
    notes1.add(new Note(e4, 42, 1));
    notes1.add(new Note(e4, 44, 1));
    notes1.add(new Note(e4, 46, 1));
    notes1.add(new Note(e4, 52, 1));
    notes1.add(new Note(g4, 26, 1));
    assertEquals(33, notes1.size());
    //overlapping notes
    notes1.add(new Note(g4, 28, 3));
    notes1.add(new Note(g4, 28, 3));
    //the overlapping note does exist in the song but doesn't get printed
    assertEquals(35, notes1.size());


    Music song = new Music(notes1);
    assertEquals(
            "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 \n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X                           \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X                                     \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                                  X                           \n" +
            " 7                                                    |                           \n" +
            " 8                 X                                            X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                                                                                \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                            X                 \n" +
            "25                 |                                            |                 \n" +
            "26                                                                             X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X                 \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X                           \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X                                     \n" +
            "37                 |                        |                                     \n" +
            "38                 |                                  X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                            X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X                           \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                            X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X                           \n" +
            "55                 |                                  |                           \n" +
            "56  X                                       X                                     \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     "
            , song.printMusic());
  }

  @Test
  public void mixTest() {
    List<Note> notes1 = new ArrayList<>();
    List<Note> notes2 = new ArrayList<>();

    Pitch e3 = new Pitch(PitchName.E, false, 3);
    Pitch g3 = new Pitch(PitchName.G, false, 3);
    Pitch b3 = new Pitch(PitchName.B, false, 3);

    notes1.add(new Note(e3, 6, 7));
    notes1.add(new Note(g3, 0, 6));
    notes1.add(new Note(g3, 8, 6));

    Music music = new Music(notes1);

    notes2.add(new Note(g3, 10, 7));
    notes2.add(new Note(b3, 4, 1));
    notes2.add(new Note(b3, 6, 1));

    Music music2 = new Music(notes2);

    assertEquals(
            "    E3   F3  F#3   G3 \n" +
                    " 0                 X  \n" +
                    " 1                 |  \n" +
                    " 2                 |  \n" +
                    " 3                 |  \n" +
                    " 4                 |  \n" +
                    " 5                 |  \n" +
                    " 6  X              |  \n" +
                    " 7  |                 \n" +
                    " 8  |              X  \n" +
                    " 9  |              |  \n" +
                    "10  |              |  \n" +
                    "11  |              |  \n" +
                    "12  |              |  \n" +
                    "13  |              |  \n" +
                    "14                 |  ", music.printMusic());

    assertEquals(
            "    G3  G#3   A3  A#3   B3 \n" +
                    " 0                         \n" +
                    " 1                         \n" +
                    " 2                         \n" +
                    " 3                         \n" +
                    " 4                      X  \n" +
                    " 5                      |  \n" +
                    " 6                      X  \n" +
                    " 7                      |  \n" +
                    " 8                         \n" +
                    " 9                         \n" +
                    "10  X                      \n" +
                    "11  |                      \n" +
                    "12  |                      \n" +
                    "13  |                      \n" +
                    "14  |                      \n" +
                    "15  |                      \n" +
                    "16  |                      \n" +
                    "17  |                      ", music2.printMusic());

    Music music3 = music.mix(music2, Music.MixType.simultaneously);
    assertEquals(
            "    E3   F3  F#3   G3  G#3   A3  A#3   B3 \n" +
                    " 0                 X                      \n" +
                    " 1                 |                      \n" +
                    " 2                 |                      \n" +
                    " 3                 |                      \n" +
                    " 4                 |                   X  \n" +
                    " 5                 |                   |  \n" +
                    " 6  X              |                   X  \n" +
                    " 7  |                                  |  \n" +
                    " 8  |              X                      \n" +
                    " 9  |              |                      \n" +
                    "10  |              |                      \n" +
                    "11  |              |                      \n" +
                    "12  |              |                      \n" +
                    "13  |              |                      \n" +
                    "14                 |                      \n" +
                    "15                 |                      \n" +
                    "16                 |                      \n" +
                    "17                 |                      ", music3.printMusic());

    Music music4 = music.mix(music2, Music.MixType.consecutively);
    assertEquals(
            "    E3   F3  F#3   G3  G#3   A3  A#3   B3 \n" +
                    " 0                 X                      \n" +
                    " 1                 |                      \n" +
                    " 2                 |                      \n" +
                    " 3                 |                      \n" +
                    " 4                 |                      \n" +
                    " 5                 |                      \n" +
                    " 6  X              |                      \n" +
                    " 7  |                                     \n" +
                    " 8  |              X                      \n" +
                    " 9  |              |                      \n" +
                    "10  |              |                      \n" +
                    "11  |              |                      \n" +
                    "12  |              |                      \n" +
                    "13  |              |                      \n" +
                    "14                 |                      \n" +
                    "15                                        \n" +
                    "16                                        \n" +
                    "17                                        \n" +
                    "18                                        \n" +
                    "19                                        \n" +
                    "20                                        \n" +
                    "21                                        \n" +
                    "22                                     X  \n" +
                    "23                                     |  \n" +
                    "24                                     X  \n" +
                    "25                                     |  \n" +
                    "26                                        \n" +
                    "27                                        \n" +
                    "28                 X                      \n" +
                    "29                 |                      \n" +
                    "30                 |                      \n" +
                    "31                 |                      \n" +
                    "32                 |                      \n" +
                    "33                 |                      \n" +
                    "34                 |                      \n" +
                    "35                 |                      ", music4.printMusic());

  }

  @Test
  public void musicEditorMethodsTest() {
    List<Note> notes1 = new ArrayList<>();
    List<Note> notes2 = new ArrayList<>();

    Pitch e3 = new Pitch(PitchName.E, false, 3);
    Pitch g3 = new Pitch(PitchName.G, false, 3);
    Pitch b3 = new Pitch(PitchName.B, false, 3);

    notes1.add(new Note(e3, 6, 7));
    notes1.add(new Note(g3, 0, 6));
    notes1.add(new Note(g3, 8, 6));

    Music music = new Music(notes1);

    notes2.add(new Note(g3, 10, 7));
    notes2.add(new Note(b3, 4, 1));
    notes2.add(new Note(b3, 6, 1));

    //test addNotes
    assertEquals(3, notes1.size());
    assertEquals(3, notes2.size());
    music.addNotes(notes2);
    assertEquals(6, notes1.size());

    //test retrieveNotes
    List<Note> notes3 = new ArrayList<>();
    notes3.add(new Note(g3, 0, 6));
    notes3.add(new Note(b3, 4, 1));
    assertEquals(notes3, music.retrieveNotes(4));
    assertEquals(6, notes1.size());

    //test removeNotes
    music.removeNotes(notes3);
    assertEquals(4, notes1.size());
    List<Note> notes4 = new ArrayList<>();
    assertEquals(notes4, music.retrieveNotes(4));
  }

  @Test
  public void consoleOutputTest() throws IOException, InvalidMidiDataException {
    Readable rd = new FileReader("inputFile/mary-little-lamb.txt");
    Appendable ap = new StringWriter();
    MusicEditor editor = new MusicEditor(rd, ap);
    CompositionBuilder<IMusicEditorModel> comp = new Music.Builder();
    IMusicEditorModel model = MusicReader.parseFile(rd, comp);
    editor.switchView(1, model);
    assertEquals(
            "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4 \n" +
            " 0                 X                                            X                 \n" +
            " 1                 |                                            |                 \n" +
            " 2                 |                                  X                           \n" +
            " 3                 |                                  |                           \n" +
            " 4                 |                        X                                     \n" +
            " 5                 |                        |                                     \n" +
            " 6                 |                                  X                           \n" +
            " 7                                                    |                           \n" +
            " 8                 X                                            X                 \n" +
            " 9                 |                                            |                 \n" +
            "10                 |                                            X                 \n" +
            "11                 |                                            |                 \n" +
            "12                 |                                            X                 \n" +
            "13                 |                                            |                 \n" +
            "14                 |                                            |                 \n" +
            "15                                                                                \n" +
            "16                 X                                  X                           \n" +
            "17                 |                                  |                           \n" +
            "18                 |                                  X                           \n" +
            "19                 |                                  |                           \n" +
            "20                 |                                  X                           \n" +
            "21                 |                                  |                           \n" +
            "22                 |                                  |                           \n" +
            "23                 |                                  |                           \n" +
            "24                 X                                            X                 \n" +
            "25                 |                                            |                 \n" +
            "26                                                                             X  \n" +
            "27                                                                             |  \n" +
            "28                                                                             X  \n" +
            "29                                                                             |  \n" +
            "30                                                                             |  \n" +
            "31                                                                             |  \n" +
            "32                 X                                            X                 \n" +
            "33                 |                                            |                 \n" +
            "34                 |                                  X                           \n" +
            "35                 |                                  |                           \n" +
            "36                 |                        X                                     \n" +
            "37                 |                        |                                     \n" +
            "38                 |                                  X                           \n" +
            "39                 |                                  |                           \n" +
            "40                 X                                            X                 \n" +
            "41                 |                                            |                 \n" +
            "42                 |                                            X                 \n" +
            "43                 |                                            |                 \n" +
            "44                 |                                            X                 \n" +
            "45                 |                                            |                 \n" +
            "46                 |                                            X                 \n" +
            "47                 |                                            |                 \n" +
            "48                 X                                  X                           \n" +
            "49                 |                                  |                           \n" +
            "50                 |                                  X                           \n" +
            "51                 |                                  |                           \n" +
            "52                 |                                            X                 \n" +
            "53                 |                                            |                 \n" +
            "54                 |                                  X                           \n" +
            "55                 |                                  |                           \n" +
            "56  X                                       X                                     \n" +
            "57  |                                       |                                     \n" +
            "58  |                                       |                                     \n" +
            "59  |                                       |                                     \n" +
            "60  |                                       |                                     \n" +
            "61  |                                       |                                     \n" +
            "62  |                                       |                                     \n" +
            "63  |                                       |                                     ",
            ap.toString());
  }

  @Test
  public void controllerAndkeyBoardHandlerTest() {
    GuiMock mock = new GuiMock();
    IMusicEditorModel model = new Music();
    Controller controller = new Controller(model, mock);

    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_LEFT).run();
    assertEquals("scrolled to left", mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_RIGHT).run();
    assertEquals("scrolled to right", mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_UP).run();
    assertEquals("scrolled up", mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_DOWN).run();
    assertEquals("scrolled down", mock.getTester());
    controller.getKbh().getKeyTypedMap().get('r').run();
    assertEquals("this is restarted",mock.getTester());
    controller.getKbh().getKeyTypedMap().get('s').run();
    assertEquals("this is started",mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_F7).run();
    assertEquals("this is the start",mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_F9).run();
    assertEquals("this is the end",mock.getTester());
    controller.getKbh().getKeyReleasedMap().get(KeyEvent.VK_SPACE).run();
    assertEquals("this is paused",mock.getTester());

    mock.testKeyBoard();
    assertEquals("key listener added",mock.getTester());
    mock.setTester2(2);
    mock.testKeyBoard();
    assertEquals("mouse listener added",mock.getTester());
  }
}
