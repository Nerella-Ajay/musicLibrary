package com.example.song.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;

import com.example.song.model.SongRowMapper;
import com.example.song.model.Song;
import com.example.song.repository.SongRepository;

@Service
public class SongH2Service implements SongRepository{
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Song> getSongs(){
        List<Song> songList = db.query("select * from song", new SongRowMapper());
        ArrayList<Song> songs = new ArrayList<>(songList);
        return songs;
    }

    @Override

    public Song getSongById(int songId){
        try{
            Song song = db.queryForObject("select * from song where songId = ?", new SongRowMapper(), int songId);
            return song;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Song addSong(Song song){
        db.update("insert int song(songName, lyricist, singer, musicDirector) values (?, ?, ?, ?)", song.getSongName(), song.getLyricist(), song.getSinger(), song.getMusicDirector());
        Song savedSong = db.queryForObject("select * from song where songName = ? and lyricist = ? and singer = ? and musicDirctor = ?", new SongRowMapper(), song.getSongName(), song.getLyricist(), song.getSinger(), song.getMusicDirector());
        return savedSong;
    }

    @Override
    pubic Song updateSong(int songId, Song song){
        if(song.getSongName() != null){
            db.update("update song set songName = ? where songId = ?", song.getSongName(), songId);
        }
        if(song.getLyricist() != null){
            db.update("update song set lyricist = ? where songId = ?", song.getLyricist(), songId);
        }
        if(song.getSinger() != null){
            db.update("update song set singer = ? where songId = ?", song.getSinger(), songId);
        }
        if(song.getMusicDirector() != null){
            db.update("update song set musicDirector = ? where songId = ?", song.getMusicDirector(), songId);
        }
        return getSongById(songId);
    }

    @Override
    public void deleteSong(int songId){
        db.update("delete from song where songId = ?", songId);
    }
}