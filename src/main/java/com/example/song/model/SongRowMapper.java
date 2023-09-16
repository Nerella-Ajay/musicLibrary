package com.example.song.model;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.song.model.Song;

public class SongRowMapper implements RowMapper<Song>{

    @Override
    public Song mapRow(ResultSet rs, int rowNum) throws SQLException{
        return new Song(
            rs.getInt("songId"),
            rs.getString("songName"),
            rs.getString("lyricist"),
            rs.getString("singer"),
            rs.getString("musicDirector")
        );
    }
}
