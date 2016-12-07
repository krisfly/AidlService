package example.kris.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AidlService extends Service {

    public static final String TAG = AidlService.class.getSimpleName();

    public AidlService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        Book book = new Book();
        book.setName("Android开发艺术探索");
        book.setPrice(28);
        mBooks.add(book);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private List<Book> mBooks = new ArrayList<>();

    private final BookManager.Stub mManager = new BookManager.Stub() {

        @Override
        public List<Book> getBooks() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getBooks() method , now the list is : " + mBooks.toString());
                if (mBooks != null) {
                    return mBooks;
                }
                return new ArrayList<>();
            }

        }

        @Override
        public Book getBook() throws RemoteException {
            return null;
        }

        @Override
        public int getBookCount() throws RemoteException {
            return 0;
        }

        @Override
        public void setBookPrice(Book book, int price) throws RemoteException {

        }

        @Override
        public void setBookName(Book book, String name) throws RemoteException {

        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            synchronized (this) {
                if (mBooks == null) {
                    mBooks = new ArrayList<>();
                }
                if (book == null) {
                    Log.e(TAG, "Book is null in In");
                    book = new Book();
                }
                //尝试修改book的参数，主要是为了观察其到客户端的反馈
                book.setPrice(2333);
                if (!mBooks.contains(book)) {
                    mBooks.add(book);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addBooks() method , now the list is : " + mBooks.toString());
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {

        }

        @Override
        public void addBookInout(Book book) throws RemoteException {

        }
    };

    private final ICat.Stub mCat = new ICat.Stub() {
        @Override
        public String getColor() throws RemoteException {
            return "get from remote service";
        }

        @Override
        public double getWeight() throws RemoteException {
            return 999.9;
        }
    };

}
