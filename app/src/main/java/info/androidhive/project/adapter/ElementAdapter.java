package info.androidhive.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.StringTokenizer;

import info.androidhive.project.R;
import info.androidhive.project.ReturnCode.Default;
import info.androidhive.project.WebService.Rest;
import info.androidhive.project.activity.DetailPostActivity;
import info.androidhive.project.activity.MainActivity;
import info.androidhive.project.activity.TagActivity;
import info.androidhive.project.model.Element;
import info.androidhive.project.model.Image;
import info.androidhive.project.model.Post;
import info.androidhive.project.model.Tag;
import info.androidhive.project.model.User;

public class ElementAdapter extends ArrayAdapter<Element> {
    Element element = null;
    final ArrayList<Element> elements = new ArrayList<>();

    Rest restAPI = new Rest();
    public ElementAdapter(Context context, ArrayList<Element> userTemps) {
        super(context, 0, userTemps);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //TODO dang ko co "timeCreate"
        // Get the data item for this position


        element = getItem(position);
        elements.add(element);
        Log.d("======================>", String.valueOf(position));
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_element, parent, false);
        }
        // Lookup view for data population
        // Return the completed view to render on screen
        /****************User************************/
        TextView user_name = (TextView) convertView.findViewById(R.id.user_name);
        TextView time_create = (TextView) convertView.findViewById(R.id.time_create);
        User user = element.getUser();
        ImageView user_image = (ImageView) convertView.findViewById(R.id.user_image);

        user_name.setText(user.getNameUser());
        time_create.setText(element.getPost().getCreatePost());

        //new ImageLoadTask(user.getImage().getSrc(), user_image).execute();
        //TODO Ảnh to quá không hiển thị được
        Picasso.with(getContext()).load(user.getImage().getSrc()).into(user_image);
        /****************Post***********************/

        ImageView imageLeft = (ImageView) convertView.findViewById(R.id.image_post_left);
        ImageView imageRightTop = (ImageView) convertView.findViewById(R.id.image_post_right_top);
        ImageView imageRightBottom = (ImageView) convertView.findViewById(R.id.image_post_right_bottom);
        RelativeLayout relativeLayoutImage = (RelativeLayout) convertView.findViewById(R.id.tableRowImage);
        TextView post_content = (TextView) convertView.findViewById(R.id.content_post);
        Post post = element.getPost();

        ArrayList<Image> images = post.getImages();

        int widthscreen = Resources.getSystem().getDisplayMetrics().widthPixels;
        if (images.size() == 0) {
            //Khong co image
            relativeLayoutImage.setVisibility(View.INVISIBLE);
        } else if (images.size() == 1) {
            //Remove imageRight
            if (relativeLayoutImage.indexOfChild(imageLeft) == -1) {
                relativeLayoutImage.addView(imageLeft);
            }
            imageRightTop.setVisibility(View.INVISIBLE);
            imageRightBottom.setVisibility(View.INVISIBLE);
            imageLeft.setVisibility(View.VISIBLE);
            //Size image left = full
            imageLeft.getLayoutParams().width = widthscreen;
            imageLeft.getLayoutParams().height = widthscreen;
            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
            } catch (Exception e) {
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                e.printStackTrace();
            }
        } else if (images.size() == 2) {
            imageLeft.setVisibility(View.VISIBLE);
            imageRightTop.setVisibility(View.VISIBLE);
            //Remove imageRightBottom
            imageRightBottom.setVisibility(View.INVISIBLE);

            imageLeft.getLayoutParams().width = widthscreen / 2;
            imageLeft.getLayoutParams().height = widthscreen / 2;
            //Moi anh chia doi man hinh
            imageRightTop.getLayoutParams().width = widthscreen / 2;
            imageRightTop.getLayoutParams().height = widthscreen / 2;

            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
                Picasso.with(getContext()).load(images.get(1).getSrc()).into(imageRightTop);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                Log.d("ERROR NÈ: ===========================>", images.get(1).getSrc().toString());
                e.printStackTrace();
            }
        } else {
            imageLeft.setVisibility(View.VISIBLE);
            imageRightTop.setVisibility(View.VISIBLE);
            imageRightBottom.setVisibility(View.VISIBLE);
            imageRightTop.getLayoutParams().width = widthscreen / 3;
            imageRightTop.getLayoutParams().height = widthscreen / 3;
            imageRightBottom.getLayoutParams().width = widthscreen / 3;
            imageRightBottom.getLayoutParams().height = widthscreen / 3;
            imageLeft.getLayoutParams().width = 2 * widthscreen / 3;
            imageLeft.getLayoutParams().height = 2 * widthscreen / 3;
            try {
                Picasso.with(getContext()).load(images.get(0).getSrc()).into(imageLeft);
                Picasso.with(getContext()).load(images.get(1).getSrc()).into(imageRightTop);
                Picasso.with(getContext()).load(images.get(2).getSrc()).into(imageRightBottom);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("ERROR NÈ: ===========================>", images.get(0).getSrc().toString());
                Log.d("ERROR NÈ: ===========================>", images.get(1).getSrc().toString());
                e.printStackTrace();
            }
        }
        post_content.setText(post.getContentPost());

        /**************Tag********************************/
        //TODO hien tai server dang tra ve ko co tag
        RelativeLayout relativeTag = (RelativeLayout) convertView.findViewById(R.id.relativeTag);
        Button tag1 = (Button) convertView.findViewById(R.id.bttag1);
        Button tag2 = (Button) convertView.findViewById(R.id.bttag2);
        //Button tag3 = (Button) convertView.findViewById(R.id.btTag3);
        TextView readmoreTag = (TextView) convertView.findViewById(R.id.readmoreTag);
        final View finalConvertView = convertView;
        if (element.getTag() != null) {
            int sizeTag = element.getTag().size();
            final Element temp = elements.get(position);
            if (sizeTag == 0) {
                relativeTag.setVisibility(View.INVISIBLE);
            } else if (sizeTag == 1) {
                tag2.setVisibility(View.INVISIBLE);
                //tag3.setVisibility(View.INVISIBLE);
                tag1.setVisibility(View.VISIBLE);
                readmoreTag.setVisibility(View.INVISIBLE);
                try {
                    tag1.setText(element.getTag().get(0).getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(0).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });

            } else if (sizeTag == 2) {
                //tag3.setVisibility(View.INVISIBLE);
                tag1.setVisibility(View.VISIBLE);
                tag2.setVisibility(View.VISIBLE);
                readmoreTag.setVisibility(View.INVISIBLE);
                try {
                    tag1.setText(element.getTag().get(0).getTag());
                    tag2.setText(element.getTag().get(1).getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(0).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
                tag2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(1).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
            } /*else *//*if (sizeTag == 3) {
                tag1.setVisibility(View.VISIBLE);
                tag2.setVisibility(View.VISIBLE);
                tag3.setVisibility(View.VISIBLE);
                readmoreTag.setVisibility(View.INVISIBLE);
                try {

                    tag1.setText(element.getTag().get(0).getTag());
                    tag2.setText(element.getTag().get(1).getTag());
                    tag3.setText(element.getTag().get(2).getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(0).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
                tag2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(1).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
                tag3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(2).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
            }*/ else {
                tag1.setVisibility(View.VISIBLE);
                tag2.setVisibility(View.VISIBLE);
                //tag3.setVisibility(View.VISIBLE);
                readmoreTag.setVisibility(View.VISIBLE);
                try {

                    tag1.setText(element.getTag().get(0).getTag());
                    tag2.setText(element.getTag().get(1).getTag());
                    //tag3.setText(element.getTag().get(2).getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tag1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(0).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
                tag2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(1).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });
                /*tag3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Post data
                        Intent myIntent = new Intent(finalConvertView.getContext(), TagActivity.class);
                        myIntent.putExtra("id_user", "1");
                        myIntent.putExtra("id_tag", temp.getTag().get(2).getIdTag());
                        finalConvertView.getContext().startActivity(myIntent);
                    }
                });*/
            }
        } else {
            relativeTag.removeAllViews();
        }

        /****************Feedback*******************/
        final Button bt_heart = (Button) convertView.findViewById(R.id.bt_heart);
        Button bt_heart_broken = (Button) convertView.findViewById(R.id.bt_heart_broken);
        //Tag tag = element.getTag();]

        bt_heart.setText(element.getPost().getCountTruePost());
        bt_heart_broken.setText(element.getPost().getCountFalsePost());
        bt_heart.setTag(position);
        bt_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Element temp = elements.get(position);
                //TODO send len server like User dang nhap vao he thong
                String urlParameters = "?id_user=" + temp.getUser().getIdUser()
                        + "&id_post=" + temp.getPost().getIdPost()
                        + "&type=true";
                new RetrieveFeedTask().execute(urlParameters);
                bt_heart.setText(element.getPost().getCountTruePost() + 1);
            }
        });
        bt_heart_broken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO send len server like dilike
                Element temp = elements.get(position);
                //TODO send len server like User dang nhap vao he thong
                String urlParameters = "?id_user=" + temp.getUser().getIdUser()
                        + "&id_post=" + temp.getPost().getIdPost()
                        + "&type=false";
                new RetrieveFeedTask().execute(urlParameters);
                bt_heart.setText(element.getPost().getCountTruePost() + 1);
            }
        });
        return convertView;

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected String doInBackground(String... urls) {
            try {
                return restAPI.asyncResponse(Default.WSURL + Default.URL_FEEDBACK + urls[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }

}
