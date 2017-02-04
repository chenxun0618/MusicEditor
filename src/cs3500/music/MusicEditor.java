package cs3500.music;

import cs3500.music.controller.Controller;
import cs3500.music.model.IMusicEditorModel;
import cs3500.music.model.Music;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.GuiMidiViewImpl;
import cs3500.music.view.GuiView;
import cs3500.music.view.GuiViewImpl;
import cs3500.music.view.IMusicEditorView;
import cs3500.music.view.MidiViewImpl;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;


import javax.sound.midi.InvalidMidiDataException;

/**
 * a music editor, it can produce a console view, a gui view and a midi view of a piece of music
 */
public class MusicEditor {
  private Readable rd;
  private Appendable ap;

  //default constructor
  public MusicEditor() {
    String s = "";
    rd = new StringReader(s);
    ap = new StringWriter();
  }

  public MusicEditor(Readable rd, Appendable ap) {
    this.rd = rd;
    this.ap = ap;
  }

  /**
   * constructs an instance of the appropriate concrete view
   * @param i index of a view(1 is "console", 2 is "gui", 3 is "midi", 4 is "guiMidi")
   * @param model an IMusicEditorModel
   * @throws IllegalArgumentException if the index is not one of 1, 2, 3, 4
   */
  public void switchView(int i, IMusicEditorModel model)
          throws InvalidMidiDataException, IOException {
    switch (i) {
      case 1:
        ap.append(model.printMusic());
        System.out.print(ap);
        break;
      case 2:
        GuiView gui = new GuiViewImpl(960, 800, model);
        new Controller(model, gui);
        break;
      case 3:
        IMusicEditorView midi = new MidiViewImpl(model);
        break;
      case 4:
        GuiView gui1 = new GuiViewImpl(960, 800, model);
        IMusicEditorView midi1 = new MidiViewImpl(model);
        GuiView guiMidiView = new GuiMidiViewImpl(gui1, midi1);
        new Controller(model, guiMidiView);
        break;
      default:
        throw new IllegalArgumentException("Invalid view type");
    }
  }


  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    String file = "";
    if (args.length == 0) {
      file = "inputFile/mystery-1.txt";
    }
    else {
      switch (Integer.parseInt(args[0])) {
        case 0:
          file = "inputFile/df-ttfaf.txt";
          break;
        case 1:
          file = "inputFile/lnl.txt";
          break;
        case 2:
          file = "inputFile/mary-little-lamb.txt";
          break;
        case 3:
          file = "inputFile/mystery-1.txt";
          break;
        case 4:
          file = "inputFile/mystery-2.txt";
          break;
        case 5:
          file = "inputFile/mystery-3.txt";
          break;
        case 6:
          file = "inputFile/zoot-lw.txt";
          break;
        case 7:
          file = "inputFile/zoot-zl.txt";
          break;
        default:
          throw new IllegalArgumentException("Invalid argument");
      }
    }

    CompositionBuilder<IMusicEditorModel> comp = new Music.Builder();
    IMusicEditorModel model = MusicReader.parseFile(new FileReader(file), comp);
    new MusicEditor().switchView(4, model);
  }
}
