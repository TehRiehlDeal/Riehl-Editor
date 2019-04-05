import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class RiehlEditor extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabPane;
	
	private Boolean test = false;

	public static void main(String[] args) {
		new RiehlEditor().setVisible(true);
	}
	
	private RiehlEditor() {
		super("The Riehl Editor v.2a");
		setSize(1024, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initialize();
	}
	
	private void initialize() {
		tabPane = new JTabbedPane();
		
		WordDocument doc = new WordDocument();
		
		tabPane.addTab(doc.getName(), doc);
		
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem newDoc = new JMenuItem("New");
		JMenuItem open = new JMenuItem("Open");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem saveAs = new JMenuItem("Save As");
		JMenuItem exit = new JMenuItem("Exit");
		
		JMenu help = new JMenu("Help");
		JMenuItem info = new JMenuItem("Info");
		
		JMenu edit = new JMenu("Edit");
		JMenuItem wrap = new JMenuItem("Word Wrap");
		
		JMenu close = new JMenu("Close Tab");
		close.addActionListener(this);
		
		JMenuItem[] items = { newDoc, open, save, saveAs, exit, info, };
		for(JMenuItem item : items){
			item.addActionListener(this);
		}
		
		file.add(newDoc);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.addSeparator();
		file.add(exit);
		
		help.add(info);
		
		edit.add(wrap);
		
		bar.add(file);
		//bar.add(edit);
		bar.add(help);
		bar.add(close);
		
		
		add(tabPane);
		setJMenuBar(bar);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Open")){
			open();
		} else if (e.getActionCommand().equals("Save")){
			save();
			test = true;
		} else if (e.getActionCommand().equals("Save As")){
			saveAs();
		} else if (e.getActionCommand().equals("Exit")){
			System.exit(0);
		} else if (e.getActionCommand().equals("New")){
			WordDocument doc = new WordDocument();
			test = false;
			tabPane.addTab(doc.getName(), doc);
			tabPane.setSelectedComponent(doc);
		} else if (e.getActionCommand().equals("Info")){
			info();
		} else if (e.getActionCommand().equals("Word Wrap")){
			WordDocument doc = new WordDocument();
			
			doc.setText();
			/* WordDocument doc = new WordDocument();
			
			JTextArea text = doc.getText();
			
			String name = doc.getName();
			
			WordDocument doc2 = new WordDocument(name, text, 1);
			
			tabPane.remove(doc);
			tabPane.addTab(doc2.getName(), doc2);
			tabPane.setSelectedComponent(doc2); */
		} else if (e.getActionCommand().equals("Close Tab")){
			tabPane.remove(tabPane.getSelectedComponent());
		}
		
	}
	
	public void open(){
		
		JFileChooser chooser = new JFileChooser();
		
		int returned = chooser.showOpenDialog(this);
		
		if(returned == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			
			WordDocument doc = new WordDocument(file.getName(), new JTextArea());
			
			tabPane.addTab(file.getName(), doc);
			
			tabPane.setSelectedComponent(doc);
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				String line;
				
				while((line = br.readLine()) != null) {
					doc.getText().append(line + "\n");
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void save() {
		WordDocument doc = (WordDocument) tabPane.getSelectedComponent();
		if (!test){
			saveAs();
		}
		else
		{
			doc.save();
		}
		
	}
	
	private void saveAs() {
		JFileChooser chooser = new JFileChooser();
		
		int returned = chooser.showSaveDialog(this);
		
		if(returned == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			
			WordDocument doc = (WordDocument) tabPane.getSelectedComponent();
			doc.saveAs(file.getAbsolutePath());

		}
	}
	
	private void info(){
		JFrame info = new JFrame("Info");
		info.setVisible(true);
		
		info.setSize(400, 200);
		
		info.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		info.setLayout(new BorderLayout());
		
		JLabel version = new JLabel("The Riehl Editor v.2a");
		version.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JLabel text = new JLabel("This little project is in an early alpha stage, thank you for using.");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		//JLabel text2 = new JLabel("");
		//text2.setHorizontalAlignment(SwingConstants.CENTER);
		
		info.add(version, BorderLayout.NORTH);
		info.add(text, BorderLayout.CENTER);
		//info.add(text2, BorderLayout.AFTER_LINE_ENDS);
		
		
	}
}
