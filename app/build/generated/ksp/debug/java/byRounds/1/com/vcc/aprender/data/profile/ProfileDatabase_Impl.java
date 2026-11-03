package com.vcc.aprender.data.profile;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.vcc.aprender.data.cards.CustomCardDao;
import com.vcc.aprender.data.cards.CustomCardDao_Impl;
import com.vcc.aprender.data.progress.AnswerHistoryDao;
import com.vcc.aprender.data.progress.AnswerHistoryDao_Impl;
import com.vcc.aprender.data.progress.ProgressDao;
import com.vcc.aprender.data.progress.ProgressDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProfileDatabase_Impl extends ProfileDatabase {
  private volatile ProfileDao _profileDao;

  private volatile ProgressDao _progressDao;

  private volatile CustomCardDao _customCardDao;

  private volatile AnswerHistoryDao _answerHistoryDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `child_profiles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `age` INTEGER NOT NULL, `avatar` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `learning_progress` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `profileId` INTEGER NOT NULL, `topicId` TEXT NOT NULL, `completedActivities` INTEGER NOT NULL, `correctAnswers` INTEGER NOT NULL, `totalAnswers` INTEGER NOT NULL, `lastActivityAt` INTEGER NOT NULL, FOREIGN KEY(`profileId`) REFERENCES `child_profiles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_learning_progress_profileId` ON `learning_progress` (`profileId`)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_learning_progress_profileId_topicId` ON `learning_progress` (`profileId`, `topicId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `custom_cards` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `topic` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `question` TEXT NOT NULL, `firstOption` TEXT NOT NULL, `secondOption` TEXT NOT NULL, `thirdOption` TEXT NOT NULL, `correctIndex` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `answer_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `profileId` INTEGER NOT NULL, `topicId` TEXT NOT NULL, `correct` INTEGER NOT NULL, `answeredAt` INTEGER NOT NULL, FOREIGN KEY(`profileId`) REFERENCES `child_profiles`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_history_profileId` ON `answer_history` (`profileId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_history_profileId_answeredAt` ON `answer_history` (`profileId`, `answeredAt`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5f74b60322ae4458bfe5f9c7163bc107')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `child_profiles`");
        db.execSQL("DROP TABLE IF EXISTS `learning_progress`");
        db.execSQL("DROP TABLE IF EXISTS `custom_cards`");
        db.execSQL("DROP TABLE IF EXISTS `answer_history`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsChildProfiles = new HashMap<String, TableInfo.Column>(4);
        _columnsChildProfiles.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildProfiles.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildProfiles.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildProfiles.put("avatar", new TableInfo.Column("avatar", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChildProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChildProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChildProfiles = new TableInfo("child_profiles", _columnsChildProfiles, _foreignKeysChildProfiles, _indicesChildProfiles);
        final TableInfo _existingChildProfiles = TableInfo.read(db, "child_profiles");
        if (!_infoChildProfiles.equals(_existingChildProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "child_profiles(com.vcc.aprender.data.profile.ChildProfile).\n"
                  + " Expected:\n" + _infoChildProfiles + "\n"
                  + " Found:\n" + _existingChildProfiles);
        }
        final HashMap<String, TableInfo.Column> _columnsLearningProgress = new HashMap<String, TableInfo.Column>(7);
        _columnsLearningProgress.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("profileId", new TableInfo.Column("profileId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("topicId", new TableInfo.Column("topicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("completedActivities", new TableInfo.Column("completedActivities", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("correctAnswers", new TableInfo.Column("correctAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("totalAnswers", new TableInfo.Column("totalAnswers", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLearningProgress.put("lastActivityAt", new TableInfo.Column("lastActivityAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLearningProgress = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysLearningProgress.add(new TableInfo.ForeignKey("child_profiles", "CASCADE", "NO ACTION", Arrays.asList("profileId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesLearningProgress = new HashSet<TableInfo.Index>(2);
        _indicesLearningProgress.add(new TableInfo.Index("index_learning_progress_profileId", false, Arrays.asList("profileId"), Arrays.asList("ASC")));
        _indicesLearningProgress.add(new TableInfo.Index("index_learning_progress_profileId_topicId", true, Arrays.asList("profileId", "topicId"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoLearningProgress = new TableInfo("learning_progress", _columnsLearningProgress, _foreignKeysLearningProgress, _indicesLearningProgress);
        final TableInfo _existingLearningProgress = TableInfo.read(db, "learning_progress");
        if (!_infoLearningProgress.equals(_existingLearningProgress)) {
          return new RoomOpenHelper.ValidationResult(false, "learning_progress(com.vcc.aprender.data.progress.LearningProgress).\n"
                  + " Expected:\n" + _infoLearningProgress + "\n"
                  + " Found:\n" + _existingLearningProgress);
        }
        final HashMap<String, TableInfo.Column> _columnsCustomCards = new HashMap<String, TableInfo.Column>(8);
        _columnsCustomCards.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("topic", new TableInfo.Column("topic", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("question", new TableInfo.Column("question", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("firstOption", new TableInfo.Column("firstOption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("secondOption", new TableInfo.Column("secondOption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("thirdOption", new TableInfo.Column("thirdOption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCustomCards.put("correctIndex", new TableInfo.Column("correctIndex", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCustomCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCustomCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCustomCards = new TableInfo("custom_cards", _columnsCustomCards, _foreignKeysCustomCards, _indicesCustomCards);
        final TableInfo _existingCustomCards = TableInfo.read(db, "custom_cards");
        if (!_infoCustomCards.equals(_existingCustomCards)) {
          return new RoomOpenHelper.ValidationResult(false, "custom_cards(com.vcc.aprender.data.cards.CustomCardEntity).\n"
                  + " Expected:\n" + _infoCustomCards + "\n"
                  + " Found:\n" + _existingCustomCards);
        }
        final HashMap<String, TableInfo.Column> _columnsAnswerHistory = new HashMap<String, TableInfo.Column>(5);
        _columnsAnswerHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerHistory.put("profileId", new TableInfo.Column("profileId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerHistory.put("topicId", new TableInfo.Column("topicId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerHistory.put("correct", new TableInfo.Column("correct", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswerHistory.put("answeredAt", new TableInfo.Column("answeredAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnswerHistory = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAnswerHistory.add(new TableInfo.ForeignKey("child_profiles", "CASCADE", "NO ACTION", Arrays.asList("profileId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAnswerHistory = new HashSet<TableInfo.Index>(2);
        _indicesAnswerHistory.add(new TableInfo.Index("index_answer_history_profileId", false, Arrays.asList("profileId"), Arrays.asList("ASC")));
        _indicesAnswerHistory.add(new TableInfo.Index("index_answer_history_profileId_answeredAt", false, Arrays.asList("profileId", "answeredAt"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoAnswerHistory = new TableInfo("answer_history", _columnsAnswerHistory, _foreignKeysAnswerHistory, _indicesAnswerHistory);
        final TableInfo _existingAnswerHistory = TableInfo.read(db, "answer_history");
        if (!_infoAnswerHistory.equals(_existingAnswerHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "answer_history(com.vcc.aprender.data.progress.AnswerHistory).\n"
                  + " Expected:\n" + _infoAnswerHistory + "\n"
                  + " Found:\n" + _existingAnswerHistory);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5f74b60322ae4458bfe5f9c7163bc107", "846e9a4bcf893440c0ae63d91799ea84");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "child_profiles","learning_progress","custom_cards","answer_history");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `child_profiles`");
      _db.execSQL("DELETE FROM `learning_progress`");
      _db.execSQL("DELETE FROM `custom_cards`");
      _db.execSQL("DELETE FROM `answer_history`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProfileDao.class, ProfileDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProgressDao.class, ProgressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CustomCardDao.class, CustomCardDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AnswerHistoryDao.class, AnswerHistoryDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProfileDao profileDao() {
    if (_profileDao != null) {
      return _profileDao;
    } else {
      synchronized(this) {
        if(_profileDao == null) {
          _profileDao = new ProfileDao_Impl(this);
        }
        return _profileDao;
      }
    }
  }

  @Override
  public ProgressDao progressDao() {
    if (_progressDao != null) {
      return _progressDao;
    } else {
      synchronized(this) {
        if(_progressDao == null) {
          _progressDao = new ProgressDao_Impl(this);
        }
        return _progressDao;
      }
    }
  }

  @Override
  public CustomCardDao customCardDao() {
    if (_customCardDao != null) {
      return _customCardDao;
    } else {
      synchronized(this) {
        if(_customCardDao == null) {
          _customCardDao = new CustomCardDao_Impl(this);
        }
        return _customCardDao;
      }
    }
  }

  @Override
  public AnswerHistoryDao answerHistoryDao() {
    if (_answerHistoryDao != null) {
      return _answerHistoryDao;
    } else {
      synchronized(this) {
        if(_answerHistoryDao == null) {
          _answerHistoryDao = new AnswerHistoryDao_Impl(this);
        }
        return _answerHistoryDao;
      }
    }
  }
}
