

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;

public class DisplayQuery extends JFrame 
{
	//database URL,username and password
	static final String DATABASE_URL="jdbc:mysql://localhost:3306/FileDB?";
		
		//GAVE PublicKeyRetrival not allowed
		//because of useSSL=false????
		//useSSL was used to handle exception
		//static final "jdbc:mysql://localhost:3306/FileDB?useSSL=false";
	
	static final String USERNAME="root";
	static final String PASSWORD="SJ9Qwq27md9XcpK";
	
	//default query retrives all data from table
	static final String DEFAULT_QUERY = "SELECT * "
										+ "	FROM Files;";
	
	private static ResultSetTableModel tableModel;
	private JTextArea queryArea;
	
	//create ResultSetModel and GUI
	
	public DisplayQuery()
	{
		super("Displaying Database Results");
		
		//create ResultSetTableModel and display database table
		try
		{
			//create TableModel for results of query SELECT * FROM FIELDS
			tableModel = new ResultSetTableModel( DATABASE_URL,
					USERNAME,PASSWORD, DEFAULT_QUERY);
			
			//set up JTextArea in which user types queries
			queryArea = new JTextArea(DEFAULT_QUERY,3,100);
			queryArea.setWrapStyleWord(true);
			queryArea.setLineWrap(true);
			
			JScrollPane scrollPane = new JScrollPane(queryArea,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			
			//set up JButton for submitting queries
			JButton submitButton = new JButton ("Submit Query");
			
			//create Box to manage placement of queryArea and
			//submitButton in GUI
			Box boxNorth = Box.createHorizontalBox();
			boxNorth.add(scrollPane);
			boxNorth.add(submitButton);
			
			
			
			
			/*
			 * will create a new Help Button that describes how to use 
			 * Submit Query Button and Filter Button
			JButton HelpButton = new JButton ("Help");
			Box boxSouth = Box.createHorizontalBox();
			boxNorth.add(scrollPane);
			boxNorth.add(submitButton);
			
			*/
			
			//create JTable based on the tableModel1
			JTable resultTable = new JTable (tableModel);
		
			
			
			//Incorporates filter Button
			JLabel filter = new JLabel ("Filter:");
			final JTextField textForFilter = new JTextField();
			JButton filterButton= new JButton ("Filter");
			Box boxSouth = Box.createHorizontalBox();
			
			boxSouth.add(filter);
			boxSouth.add(textForFilter);
			boxSouth.add(filterButton);
			
			
			//placeGUI components on content pane
			add( boxNorth, BorderLayout.NORTH);
			add(new JScrollPane(resultTable), BorderLayout.CENTER);
			add( boxSouth,BorderLayout.SOUTH);
			
			
		//create event listener for submitButton
			submitButton.addActionListener(
					
					new ActionListener()
					{
						//pass query to table model
						public void actionPerformed(ActionEvent event)
						{
							//perform a new query
							try
							{
								tableModel.setQuery(queryArea.getText());
							}//end try
							catch (SQLException sqlException)
							{
								JOptionPane.showMessageDialog(null,
										sqlException.getMessage(), "Database Error",
										JOptionPane.ERROR_MESSAGE);
								
								//try to recover from invalid user query
								//by executing default query
								try
								{
									tableModel.setQuery(DEFAULT_QUERY);
									queryArea.setText(DEFAULT_QUERY);
								}//end try
								catch(SQLException sqlException2 )
								{
									JOptionPane.showMessageDialog(null, 
											sqlException2.getMessage(), "Database error",
											JOptionPane.ERROR_MESSAGE);
									
									//ensure database connection is closed
									tableModel.disconnectFromDatabase();
									
									System.exit(1); //terminate application
								}//end inner catch	
							}//end outer catch
						}//end action Performed
					}//end ActionListener inner class	
				) ;//end call to addActionListener
	
	final TableRowSorter<TableModel>sorter=
			new TableRowSorter<TableModel>(tableModel);
	resultTable.setRowSorter(sorter);
	setSize(500,250);
	setVisible(true);
	
	//create listener for filterButton
	
	filterButton.addActionListener(
			new ActionListener()
			{
				//pass filter text to listener
				public void actionPerformed(ActionEvent e)
				{
					String text = textForFilter.getText();
					
					if (text.length()==0)
						sorter.setRowFilter(null);
					else
					{
						try
						{
							sorter.setRowFilter(
									RowFilter.regexFilter(text));
						}//end try
						catch (PatternSyntaxException pse)
						{
							JOptionPane.showMessageDialog(null,
									"Bad regex patterm", "Bad regex pattern",
									JOptionPane.ERROR_MESSAGE);
						}//end catch
					}//end else
				}//end method actionPerformed
			}//end annonymous inner class
			);//end call to addAction inner class
			
	}//end try
	catch (SQLException sqlException)
	{
		JOptionPane.showMessageDialog(null, sqlException.getMessage(),
				"Database error", JOptionPane.ERROR_MESSAGE);
		
		//ensure database connection is closed
		tableModel.disconnectFromDatabase();
		
		System.exit(1 );//terminate application
	}// end catch
	
	//dispose of window when user quits application (this overrides
	//the default of HIDE_ON_CLOSE)
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	//ensure database connection is closed when user quits application
	addWindowListener(
			//disconnect from database and exit when window has closed
			new WindowAdapter()
			{
				public void windowClosed(WindowEvent event)
				{
					tableModel.disconnectFromDatabase();
					System.exit(0);
				}//end method windowClosed
			}//endWindowAdapter inner class
			);
}
}
	