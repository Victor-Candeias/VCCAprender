package com.vcc.aprender.data.cards;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CustomCardDao_Impl implements CustomCardDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CustomCardEntity> __insertionAdapterOfCustomCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  public CustomCardDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCustomCardEntity = new EntityInsertionAdapter<CustomCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `custom_cards` (`id`,`topic`,`difficulty`,`question`,`firstOption`,`secondOption`,`thirdOption`,`correctIndex`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CustomCardEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTopic());
        statement.bindString(3, entity.getDifficulty());
        statement.bindString(4, entity.getQuestion());
        statement.bindString(5, entity.getFirstOption());
        statement.bindString(6, entity.getSecondOption());
        statement.bindString(7, entity.getThirdOption());
        statement.bindLong(8, entity.getCorrectIndex());
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM custom_cards WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object save(final CustomCardEntity card, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCustomCardEntity.insertAndReturnId(card);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object importAll(final List<CustomCardEntity> cards,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> CustomCardDao.DefaultImpls.importAll(CustomCardDao_Impl.this, cards, __cont), $completion);
  }

  @Override
  public Object delete(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CustomCardEntity>> observeAll() {
    final String _sql = "SELECT * FROM custom_cards ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"custom_cards"}, new Callable<List<CustomCardEntity>>() {
      @Override
      @NonNull
      public List<CustomCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfFirstOption = CursorUtil.getColumnIndexOrThrow(_cursor, "firstOption");
          final int _cursorIndexOfSecondOption = CursorUtil.getColumnIndexOrThrow(_cursor, "secondOption");
          final int _cursorIndexOfThirdOption = CursorUtil.getColumnIndexOrThrow(_cursor, "thirdOption");
          final int _cursorIndexOfCorrectIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "correctIndex");
          final List<CustomCardEntity> _result = new ArrayList<CustomCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CustomCardEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpQuestion;
            _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            final String _tmpFirstOption;
            _tmpFirstOption = _cursor.getString(_cursorIndexOfFirstOption);
            final String _tmpSecondOption;
            _tmpSecondOption = _cursor.getString(_cursorIndexOfSecondOption);
            final String _tmpThirdOption;
            _tmpThirdOption = _cursor.getString(_cursorIndexOfThirdOption);
            final int _tmpCorrectIndex;
            _tmpCorrectIndex = _cursor.getInt(_cursorIndexOfCorrectIndex);
            _item = new CustomCardEntity(_tmpId,_tmpTopic,_tmpDifficulty,_tmpQuestion,_tmpFirstOption,_tmpSecondOption,_tmpThirdOption,_tmpCorrectIndex);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAll(final Continuation<? super List<CustomCardEntity>> $completion) {
    final String _sql = "SELECT * FROM custom_cards ORDER BY id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CustomCardEntity>>() {
      @Override
      @NonNull
      public List<CustomCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTopic = CursorUtil.getColumnIndexOrThrow(_cursor, "topic");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfFirstOption = CursorUtil.getColumnIndexOrThrow(_cursor, "firstOption");
          final int _cursorIndexOfSecondOption = CursorUtil.getColumnIndexOrThrow(_cursor, "secondOption");
          final int _cursorIndexOfThirdOption = CursorUtil.getColumnIndexOrThrow(_cursor, "thirdOption");
          final int _cursorIndexOfCorrectIndex = CursorUtil.getColumnIndexOrThrow(_cursor, "correctIndex");
          final List<CustomCardEntity> _result = new ArrayList<CustomCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CustomCardEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTopic;
            _tmpTopic = _cursor.getString(_cursorIndexOfTopic);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpQuestion;
            _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            final String _tmpFirstOption;
            _tmpFirstOption = _cursor.getString(_cursorIndexOfFirstOption);
            final String _tmpSecondOption;
            _tmpSecondOption = _cursor.getString(_cursorIndexOfSecondOption);
            final String _tmpThirdOption;
            _tmpThirdOption = _cursor.getString(_cursorIndexOfThirdOption);
            final int _tmpCorrectIndex;
            _tmpCorrectIndex = _cursor.getInt(_cursorIndexOfCorrectIndex);
            _item = new CustomCardEntity(_tmpId,_tmpTopic,_tmpDifficulty,_tmpQuestion,_tmpFirstOption,_tmpSecondOption,_tmpThirdOption,_tmpCorrectIndex);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
