package com.vcc.aprender.data.progress;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
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
public final class AnswerHistoryDao_Impl implements AnswerHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AnswerHistory> __insertionAdapterOfAnswerHistory;

  public AnswerHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnswerHistory = new EntityInsertionAdapter<AnswerHistory>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `answer_history` (`id`,`profileId`,`topicId`,`correct`,`answeredAt`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AnswerHistory entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProfileId());
        statement.bindString(3, entity.getTopicId());
        final int _tmp = entity.getCorrect() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getAnsweredAt());
      }
    };
  }

  @Override
  public Object insert(final AnswerHistory answer, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAnswerHistory.insert(answer);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<AnswerHistory>> observeByProfile(final long profileId) {
    final String _sql = "SELECT * FROM answer_history WHERE profileId = ? ORDER BY answeredAt DESC, id DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"answer_history"}, new Callable<List<AnswerHistory>>() {
      @Override
      @NonNull
      public List<AnswerHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "correct");
          final int _cursorIndexOfAnsweredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "answeredAt");
          final List<AnswerHistory> _result = new ArrayList<AnswerHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AnswerHistory _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpTopicId;
            _tmpTopicId = _cursor.getString(_cursorIndexOfTopicId);
            final boolean _tmpCorrect;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCorrect);
            _tmpCorrect = _tmp != 0;
            final long _tmpAnsweredAt;
            _tmpAnsweredAt = _cursor.getLong(_cursorIndexOfAnsweredAt);
            _item = new AnswerHistory(_tmpId,_tmpProfileId,_tmpTopicId,_tmpCorrect,_tmpAnsweredAt);
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
  public Object recent(final long profileId, final int limit,
      final Continuation<? super List<AnswerHistory>> $completion) {
    final String _sql = "SELECT * FROM answer_history WHERE profileId = ? ORDER BY answeredAt DESC, id DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AnswerHistory>>() {
      @Override
      @NonNull
      public List<AnswerHistory> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "correct");
          final int _cursorIndexOfAnsweredAt = CursorUtil.getColumnIndexOrThrow(_cursor, "answeredAt");
          final List<AnswerHistory> _result = new ArrayList<AnswerHistory>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AnswerHistory _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpTopicId;
            _tmpTopicId = _cursor.getString(_cursorIndexOfTopicId);
            final boolean _tmpCorrect;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfCorrect);
            _tmpCorrect = _tmp != 0;
            final long _tmpAnsweredAt;
            _tmpAnsweredAt = _cursor.getLong(_cursorIndexOfAnsweredAt);
            _item = new AnswerHistory(_tmpId,_tmpProfileId,_tmpTopicId,_tmpCorrect,_tmpAnsweredAt);
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
