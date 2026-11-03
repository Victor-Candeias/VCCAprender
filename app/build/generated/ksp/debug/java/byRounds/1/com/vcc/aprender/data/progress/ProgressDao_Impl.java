package com.vcc.aprender.data.progress;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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
public final class ProgressDao_Impl implements ProgressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LearningProgress> __insertionAdapterOfLearningProgress;

  private final SharedSQLiteStatement __preparedStmtOfDeleteForProfile;

  public ProgressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLearningProgress = new EntityInsertionAdapter<LearningProgress>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `learning_progress` (`id`,`profileId`,`topicId`,`completedActivities`,`correctAnswers`,`totalAnswers`,`lastActivityAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LearningProgress entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProfileId());
        statement.bindString(3, entity.getTopicId());
        statement.bindLong(4, entity.getCompletedActivities());
        statement.bindLong(5, entity.getCorrectAnswers());
        statement.bindLong(6, entity.getTotalAnswers());
        statement.bindLong(7, entity.getLastActivityAt());
      }
    };
    this.__preparedStmtOfDeleteForProfile = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM learning_progress WHERE profileId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final LearningProgress progress,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLearningProgress.insert(progress);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteForProfile(final long profileId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteForProfile.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, profileId);
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
          __preparedStmtOfDeleteForProfile.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LearningProgress>> observeByProfile(final long profileId) {
    final String _sql = "SELECT * FROM learning_progress WHERE profileId = ? ORDER BY topicId";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"learning_progress"}, new Callable<List<LearningProgress>>() {
      @Override
      @NonNull
      public List<LearningProgress> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfCompletedActivities = CursorUtil.getColumnIndexOrThrow(_cursor, "completedActivities");
          final int _cursorIndexOfCorrectAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswers");
          final int _cursorIndexOfTotalAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAnswers");
          final int _cursorIndexOfLastActivityAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActivityAt");
          final List<LearningProgress> _result = new ArrayList<LearningProgress>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LearningProgress _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpTopicId;
            _tmpTopicId = _cursor.getString(_cursorIndexOfTopicId);
            final int _tmpCompletedActivities;
            _tmpCompletedActivities = _cursor.getInt(_cursorIndexOfCompletedActivities);
            final int _tmpCorrectAnswers;
            _tmpCorrectAnswers = _cursor.getInt(_cursorIndexOfCorrectAnswers);
            final int _tmpTotalAnswers;
            _tmpTotalAnswers = _cursor.getInt(_cursorIndexOfTotalAnswers);
            final long _tmpLastActivityAt;
            _tmpLastActivityAt = _cursor.getLong(_cursorIndexOfLastActivityAt);
            _item = new LearningProgress(_tmpId,_tmpProfileId,_tmpTopicId,_tmpCompletedActivities,_tmpCorrectAnswers,_tmpTotalAnswers,_tmpLastActivityAt);
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
  public Object find(final long profileId, final String topicId,
      final Continuation<? super LearningProgress> $completion) {
    final String _sql = "SELECT * FROM learning_progress WHERE profileId = ? AND topicId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, profileId);
    _argIndex = 2;
    _statement.bindString(_argIndex, topicId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<LearningProgress>() {
      @Override
      @Nullable
      public LearningProgress call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProfileId = CursorUtil.getColumnIndexOrThrow(_cursor, "profileId");
          final int _cursorIndexOfTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topicId");
          final int _cursorIndexOfCompletedActivities = CursorUtil.getColumnIndexOrThrow(_cursor, "completedActivities");
          final int _cursorIndexOfCorrectAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "correctAnswers");
          final int _cursorIndexOfTotalAnswers = CursorUtil.getColumnIndexOrThrow(_cursor, "totalAnswers");
          final int _cursorIndexOfLastActivityAt = CursorUtil.getColumnIndexOrThrow(_cursor, "lastActivityAt");
          final LearningProgress _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProfileId;
            _tmpProfileId = _cursor.getLong(_cursorIndexOfProfileId);
            final String _tmpTopicId;
            _tmpTopicId = _cursor.getString(_cursorIndexOfTopicId);
            final int _tmpCompletedActivities;
            _tmpCompletedActivities = _cursor.getInt(_cursorIndexOfCompletedActivities);
            final int _tmpCorrectAnswers;
            _tmpCorrectAnswers = _cursor.getInt(_cursorIndexOfCorrectAnswers);
            final int _tmpTotalAnswers;
            _tmpTotalAnswers = _cursor.getInt(_cursorIndexOfTotalAnswers);
            final long _tmpLastActivityAt;
            _tmpLastActivityAt = _cursor.getLong(_cursorIndexOfLastActivityAt);
            _result = new LearningProgress(_tmpId,_tmpProfileId,_tmpTopicId,_tmpCompletedActivities,_tmpCorrectAnswers,_tmpTotalAnswers,_tmpLastActivityAt);
          } else {
            _result = null;
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
