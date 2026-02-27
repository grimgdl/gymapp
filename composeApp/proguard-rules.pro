


-keepattributes *Annotation*
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao

-keepclassmembers class * extends androidx.room.RoomDatabase {
    <init>(...);
}

-keep class * {
    @androidx.room.Database *;
}