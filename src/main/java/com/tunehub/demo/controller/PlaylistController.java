package com.tunehub.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tunehub.demo.entities.Playlist;
import com.tunehub.demo.entities.Song;
import com.tunehub.demo.services.PlaylistService;
import com.tunehub.demo.services.SongService;

@Controller
public class PlaylistController 
{
	@Autowired
    SongService songService;
	
	@Autowired
	PlaylistService playlistService;
    
	@GetMapping("/createPlaylist")
	public String createPlaylist(Model model)
	{
	    List<Song> songList =songService.fetchAllSongs();
	    model.addAttribute("songs", songList);
	    
	    return "createPlayList";
	}
	
	@PostMapping("/addPlaylist")
	public String addPlayList(@ModelAttribute Playlist playlist)
	{
		//updating playlist table
		playlistService.addPlaylist(playlist);
		
		//updating song table
		List<Song> songList = playlist.getSongs();
		for(Song s : songList)
		{
			s.getPlaylists().add(playlist);
			//update song object in database
			songService.updateSong(s);
		}
		return "adminHome";
	}
	
	@GetMapping("/viewPlaylists")
	public String viewPlaylists(Model model)
	{
		List<Playlist> allPlaylists = playlistService.fetchAllPlaylists();
		model.addAttribute("allPlaylists", allPlaylists);
		return "displayPlaylists";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}