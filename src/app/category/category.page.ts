import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-category',
  templateUrl: './category.page.html',
  styleUrls: ['./category.page.scss'],
})
export class CategoryPage implements OnInit {
  categories = [
    { label: 'Animals', value: 'animals' },
    { label: 'brain teasers', value: 'brain-teasers' },
    { label: 'celebrities', value: 'celebrities' },
    { label: 'entertainment', value: 'entertainment' },
    { label: 'kids', value: 'for-kids' },
    { label: 'general', value: 'general' },
    { label: 'geography', value: 'geography' },
    { label: 'history', value: 'history' },
    { label: 'hobbies', value: 'hobbies' },
    { label: 'humanity', value: 'humanities' },
    { label: 'literature', value: 'literature' },
    { label: 'movie', value: 'movies' },
    { label: 'music', value: 'music' },
    { label: 'newest', value: 'newest' },
    { label: 'people', value: 'people' },
    { label: 'religion', value: 'religion-faith' },
    { label: 'science & tech', value: 'science-technology' },
    { label: 'sport', value: 'sports' },
    { label: 'television', value: 'television' },
    { label: 'games', value: 'video-games' },
    { label: 'world', value: 'world' },
  ];
  constructor() {}

  ngOnInit() {}
}
