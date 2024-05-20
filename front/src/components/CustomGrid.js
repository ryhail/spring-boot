import * as React from 'react';
import Grid from '@mui/material/Grid';
import axios from "axios";
import {Paper} from "@mui/material";
import {styled} from "@mui/material/styles";
import Typography from "@mui/material/Typography";
import {useNavigate} from "react-router-dom";

const Img = styled('img')({
    margin: 'left',
    display: 'block',
    maxWidth: '100%',
    maxHeight: '100%',
});

export default function CustomGrid(endpoint) {
    const [objects, setObjects] = React.useState(null);
    let url = 'http://localhost:8080/anime';
    React.useEffect(() => {
        axios.get(url).then((response) => {
            setObjects(response.data);
        })
    }, []);
    const navigate = useNavigate();
    const handleImageClick = (id) => {
        console.log(id);
        navigate("/animes?id=" + id);
    }
    return (
        <Grid container spacing={3} sx={{margin: "auto"}}> {
            objects?.map((object) => (
                <Grid item xs={4} >
                    <Paper
                        sx={{
                            margin: "auto",
                            width: "auto",
                            height: "auto",
                            backgroundColor: (theme) =>
                                theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
                        }}
                        onClick={event => handleImageClick(object.id)}
                    >

                   <Grid container>
                       <Grid item xs={12}>
                           <Grid container spacing={2}>
                               <Grid item xs ={5}>
                                   <Img src={object.image}/>
                               </Grid>
                               <Grid item xs={7}>
                                   <Grid container direction="column" spacing={2}>
                                           <Grid item xs={3}>
                                               <Typography variant='body1' sx={{margin: "auto"}}>
                                                   {object.russian}
                                               </Typography>
                                           </Grid>
                                           <Grid item xs={3}>
                                           <Typography  sx={{margin: "auto"}}>
                                               {object.name}
                                           </Typography>
                                           </Grid>
                                           <Grid item xs={3}>
                                           <Typography sx={{margin: "auto"}}>
                                               Запущено {object.aired_on}
                                           </Typography>
                                           </Grid>
                                           <Grid item></Grid>
                                   </Grid>
                               </Grid>
                           </Grid>
                       </Grid>
                   </Grid>
                   </Paper>
                </Grid>
                )
            )}
        </Grid>
    )
}