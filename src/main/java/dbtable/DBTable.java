/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtable;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author P14A-Fanjava
 */
public class DBTable {
	public String toUpper(String s)
	{
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}

	public void insert(Connection con) throws Exception
	{
		String nomTable=getClass().getSimpleName();
		String [] lesAttributs=new String[getClass().getDeclaredFields().length];
		String tousLesAtr=new String();
		String allValues=new String();

		for(int i=1;i<lesAttributs.length;i++)
		{
			lesAttributs[i]=getClass().getDeclaredFields()[i].getName();
			lesAttributs[i]=toUpper(lesAttributs[i]);
			Object attributValue=getClass().getMethod("get"+lesAttributs[i]).invoke(this);
			tousLesAtr+=lesAttributs[i];

			if((attributValue==null && i==0) || (attributValue instanceof Integer && (int)attributValue==0) && i==0)
			{
				allValues+="seq"+nomTable+".nextval";
			}
			else if(attributValue!=null)
			{
				allValues+="'"+attributValue.toString()+"'";
			}
			else if(attributValue==null && i!=0)
			{
				allValues+="null";
			}

			if(i<lesAttributs.length-1)
			{
				tousLesAtr+=",";
				allValues+=",";
			}
		}
		String requete = "insert into "+nomTable+" ("+tousLesAtr+") values ("+allValues+")";
		System.out.println(requete);
		PreparedStatement stmt = con.prepareStatement(requete);

		stmt.executeUpdate();
                stmt.close();
	}

	public void delete(Connection con) throws Exception
	{
		String req = "delete from "+getClass().getSimpleName()+" where ";
		String [] lesAttributs=new String[getClass().getDeclaredFields().length];

		for(int i=0;i<lesAttributs.length;i++)
		{
			lesAttributs[i]=getClass().getDeclaredFields()[i].getName();
			lesAttributs[i]=toUpper(lesAttributs[i]);
			Object attributValue=getClass().getMethod("get"+lesAttributs[i]).invoke(this);

			if(attributValue!=null)
			{
				req+=lesAttributs[i]+"=";
				req+="'"+attributValue.toString()+"'";
			}
			else
			{
				req+=lesAttributs[i];
				req+=" is null";
			}
			if(i<lesAttributs.length-1)
			{
				req+=" and ";
			}
		}
		PreparedStatement stmt = con.prepareStatement(req);
		stmt.executeUpdate();
                stmt.close();
	}

	public void update(Connection con) throws Exception
	{
		String req="update "+getClass().getSimpleName()+" set ";
		String [] attributsName=new String[getClass().getDeclaredFields().length];
		int count=0;

		for(int i=0;i<attributsName.length;i++)
		{
			attributsName[i]=getClass().getDeclaredFields()[i].getName();
			attributsName[i]=toUpper(attributsName[i]);
			Object attributsValues=getClass().getMethod("get"+attributsName[i]).invoke(this);

			if((attributsValues instanceof String && attributsValues!=null) || (attributsValues instanceof Integer && (int)attributsValues!=0)|| (attributsValues instanceof Double && (double)attributsValues!=0))
			{
				if(count==0) req+=attributsName[i]+"="+"'"+attributsValues.toString()+"'";
				if(count>0)	req+=","+attributsName[i]+"="+"'"+attributsValues.toString()+"'";
				count++;
			}
		}

		req+=" where ";
		for(int i=0;i<attributsName.length;i++)
		{
			attributsName[i]=getClass().getDeclaredFields()[i].getName();
			attributsName[i]=toUpper(attributsName[i]);
			Object attributValue=getClass().getMethod("get"+attributsName[i]).invoke(this);

			if(attributsName[i].equalsIgnoreCase("Id"))
			{
				req+=attributsName[i]+"="; req+="'"+attributValue.toString()+"'";
			}
		}
		System.out.println(req);
		PreparedStatement stmt = con.prepareStatement(req);
		stmt.executeUpdate();
                stmt.close();
	}

	public DBTable getDbObject(ResultSet r) throws Exception
	{
		Object DBTable=getClass().newInstance();

		Field [] attrObj=getClass().getDeclaredFields();
		String[] nomAttr=new String[getClass().getDeclaredFields().length];

		for(int i=0;i<attrObj.length;i++)
		{
			nomAttr[i]=attrObj[i].getName();
			Class classType=attrObj[i].getType();
			String allType=classType.getSimpleName();
			Object resultDb=new Object();

			try
			{
				resultDb=r.getString(nomAttr[i]);
				if(resultDb!=null)
				{
					if(allType.equalsIgnoreCase("int"))
					{
						resultDb=Integer.parseInt((String)resultDb);
					}
					else if(allType.equalsIgnoreCase("double"))
					{
						resultDb=Double.parseDouble((String)resultDb);
					}
					else
					{
						resultDb=(String)resultDb;
					}
					DBTable.getClass().getMethod("set"+toUpper(nomAttr[i]),classType).invoke(DBTable,resultDb);
				}
			}
			catch(java.sql.SQLException ee){}

		}
		return (DBTable)DBTable;
	}
	public DBTable[] find(String requete,Connection con) throws Exception
	{
		java.sql.Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(requete);
		Vector DBTable=new Vector();
		while(res.next())
		{
			DBTable.addElement(getDbObject(res));
		}
		DBTable[] allDb=new DBTable[DBTable.size()];
		for(int i=0;i<allDb.length;i++)
		{
			allDb[i]=((DBTable)DBTable.elementAt(i));
		}
                stmt.close();
                res.close();
		return allDb;
	}

	public DBTable[] find(Object filtre,Connection con) throws Exception
	{
		String req="select * from "+filtre.getClass().getSimpleName()+" where ";
		String [] attributsFilter=new String[filtre.getClass().getDeclaredFields().length];
		int count=0;

		for(int i=0; i<attributsFilter.length; i++)
		{
			attributsFilter[i]=toUpper(filtre.getClass().getDeclaredFields()[i].getName());
			Object filterValues=filtre.getClass().getMethod("get"+attributsFilter[i]).invoke(filtre);

			if((filterValues instanceof String && filterValues!=null) || (filterValues instanceof Integer && (int)filterValues!=0))
			{
				if (count==0)req+=attributsFilter[i]+"= '"+filterValues.toString()+"'";
				if (count>0)req+=" and "+attributsFilter[i]+"='"+filterValues.toString()+"'";
				count++;
			}
		}
//		System.out.println(req);

		java.sql.Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(req);
		Vector DBTable=new Vector();
		while(res.next())
		{
			DBTable.addElement(getDbObject(res));
		}
		DBTable[] allDb=new DBTable[DBTable.size()];
		for(int i=0;i<allDb.length;i++)
		{
			allDb[i]=((DBTable)DBTable.elementAt(i));
		}
                stmt.close();
                res.close();
		return allDb;
	}
}