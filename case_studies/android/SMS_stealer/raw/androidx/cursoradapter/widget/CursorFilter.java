package androidx.cursoradapter.widget;

import android.database.Cursor;
import android.widget.Filter;

class CursorFilter extends Filter {
  CursorFilterClient mClient;
  
  CursorFilter(CursorFilterClient paramCursorFilterClient) {
    this.mClient = paramCursorFilterClient;
  }
  
  public CharSequence convertResultToString(Object paramObject) {
    return this.mClient.convertToString((Cursor)paramObject);
  }
  
  protected Filter.FilterResults performFiltering(CharSequence paramCharSequence) {
    Cursor cursor = this.mClient.runQueryOnBackgroundThread(paramCharSequence);
    Filter.FilterResults filterResults = new Filter.FilterResults();
    if (cursor != null) {
      filterResults.count = cursor.getCount();
      filterResults.values = cursor;
    } else {
      filterResults.count = 0;
      filterResults.values = null;
    } 
    return filterResults;
  }
  
  protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults) {
    Cursor cursor = this.mClient.getCursor();
    if (paramFilterResults.values != null && paramFilterResults.values != cursor)
      this.mClient.changeCursor((Cursor)paramFilterResults.values); 
  }
  
  static interface CursorFilterClient {
    void changeCursor(Cursor param1Cursor);
    
    CharSequence convertToString(Cursor param1Cursor);
    
    Cursor getCursor();
    
    Cursor runQueryOnBackgroundThread(CharSequence param1CharSequence);
  }
}


/* Location:              C:\Users\Jason\Downloads\355cd2b71db971dfb0fac1fc391eb4079e2b090025ca2cdc83d4a22a0ed8f082-dex2jar.jar!\androidx\cursoradapter\widget\CursorFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */