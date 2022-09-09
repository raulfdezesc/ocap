package es.jcyl.fqs.ocap.util.reports.conceptos;

import net.sf.jasperreports.engine.JRRewindableDataSource;

public abstract interface IConceptoReport extends JRRewindableDataSource
{
  public abstract void addRow();

  public abstract void addRow(int paramInt);

  public abstract void getRow(int paramInt);

  public abstract void deleteRow(int paramInt);

  public abstract int size();

  public abstract void putElement(String paramString, Object paramObject);

  public abstract Object getElement(String paramString);

  public abstract void deleteElement(String paramString);

  public abstract void moveFirst();
}

