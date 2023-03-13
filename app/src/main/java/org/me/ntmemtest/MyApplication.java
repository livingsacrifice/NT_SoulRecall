package org.me.ntmemtest;

import android.app.Application;

//import org.me.ntmemtest.data.Book;
import org.me.ntmemtest.DataBaseHelper;

public class MyApplication extends Application {

   private DataBaseHelper dataHelper;
   //private Book selectedBook;

   @Override
   public void onCreate() {
      super.onCreate();
      dataHelper = new DataBaseHelper(this);
   }

   @Override
   public void onTerminate() {
      // NOTE - this method is not guaranteed to be called
      //dataHelper.cleanup();
      //selectedBook = null;
      super.onTerminate();
   }

   public DataBaseHelper getDataHelper() {
      return dataHelper;
   }

   public void setDataHelper(DataBaseHelper dataHelper) {
      this.dataHelper = dataHelper;
   }
   /*
   public Book getSelectedBook() {
      return selectedBook;
   }

   public void setSelectedBook(Book selectedBook) {
      this.selectedBook = selectedBook;
   }

   // so that onSaveInstanceState/onRestoreInstanceState can use with just saved title
   public void establishSelectedBook(String title) {
      selectedBook = dataHelper.selectBook(title);
   }*/
}
