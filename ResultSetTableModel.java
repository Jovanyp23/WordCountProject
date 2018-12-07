
// A TableModel that supplies ResultSet data to a JTable.
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ResultSetTableModel extends AbstractTableModel
{
	
	//variable that contains query results
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	//will be used to establish connection to database
	
	private final Connection connect;
	//variable used to store Statement for querying database
	private final Statement state;
	//will be used to keep track of database connection
	private boolean connectedToDatabase = false;

	// constructor initializes resultSet and obtains its metadata object;
	// determines number of rows
	public ResultSetTableModel(String url, String username, String password, String query) throws SQLException
	{
			// connect to database
			connect = DriverManager.getConnection(url, username, password);

			//create Statement to query database
			//INSENSITIVE The cursor can scroll forward and backward, and the 
			//result set is not sensitive to changes made by others to the database
			//that occur after the result set was created.
			//The cursor can scroll forward and backward, and the result set is 
			//sensitive to changes made by others to the database that occur after
			//the result set was created.
			state = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			//update database connection status
			connectedToDatabase = true;

			//set query and execute it
			setQuery(query);
	}

	//get class that represents column type
	public Class ColumnName(int column)throws IllegalStateException
	{
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// determine Java class of column
		try
		{
			//Returns the fully-qualified name of the Java class whose instances
			//are manufactured if the method ResultSet.getObject is called to 
			//retrieve a value from the column.
			String nameOfClass = metaData.getColumnClassName(column + 1);

			// return Class object that represents className
			return Class.forName(nameOfClass);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}

		return Object.class; // if problems occur above, assume type Object
	}

	// get number of columns in ResultSet
	public int getColumnCount() throws IllegalStateException
	{
		// ensure database connection is available
		if (connectedToDatabase)
		{
				//this will get the number of columns and pass it to function
				try
				{
					return metaData.getColumnCount();
				}
				catch (SQLException sqlException)
				{
					sqlException.printStackTrace();
				}
		}
	
		else
		{
			throw new IllegalStateException("Not Connected to Database");
		}

		return 0; // if problems occur above, return 0 for number of columns
	}

	//get name of a particular column in ResultSet
	public String getColumnName(int column) throws IllegalStateException
	{
		// ensure database connection is available
		if (connectedToDatabase)
		{
				// determine column name
				try
				{
					return metaData.getColumnName(column + 1);
				}
				catch (SQLException sqlException)
				{
					sqlException.printStackTrace();
				}
		}
		else {
			
			throw new IllegalStateException("Not Connected to Database");
		}

		return ""; // if problems, return empty string for column name
	}

	// return number of rows in ResultSet
	public int getRowCount() throws IllegalStateException
	{
		// ensure database connection is available
		if (!connectedToDatabase)
				throw new IllegalStateException("Not Connected to Database");

		return numberOfRows;
	}

	// obtain value in particular row and column
	public Object getValueAt(int row,int column)
			throws IllegalStateException
	{
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// obtain a value at specified ResultSet row and column
		try
		{
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		}
		catch (SQLException sqlException)
		{
			sqlException.printStackTrace();
		}

		return ""; // if problems, return empty string object
	}

	//set new database query string
	public void setQuery(String query)
			throws SQLException, IllegalStateException
	{
			// ensure database connection is available
			if (!connectedToDatabase)
				throw new IllegalStateException("Not Connected to Database");

			// specify query and execute it
			resultSet =state.executeQuery(query);

			// obtain metadata for ResultSet
			metaData = resultSet.getMetaData();

			// determine number of rows in ResultSet
			resultSet.last(); // move to last row
			numberOfRows = resultSet.getRow(); // get row number

			//notify JTable that model has changed
			fireTableStructureChanged();
	}

	//close Statement and Connection
	public void disconnectFromDatabase()
	{
		if (connectedToDatabase)
		{
			//close Statement and Connection
			try
			{
				resultSet.close();
				state.close();
				connect.close();
			}
			catch (SQLException sqlException)
			{
				sqlException.printStackTrace();
			}
			finally // update database connection status
			{
				connectedToDatabase = false;
			}
		}
	}
}//end class RseultSetTableModel

